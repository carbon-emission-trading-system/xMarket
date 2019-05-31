
package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.redis.UserOrderRedis;
import com.stock.xMarket.repository.OrderRepository;
import com.stock.xMarket.service.OrderService;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderServiceImpl.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	/** The order repository. */
	@Autowired
    private OrderRepository orderRepository ;
	
	/** The order redis. */
	@Autowired
	private OrderRedis orderRedis;
	
	@Autowired
	private UserOrderRedis userOrderRedis;
	
	

	/**
	 * Adds the order to redis.
	 *
	 * @param order the order
	 */
	@Override
	public void addOrderToRedis(Order order) {
		// TODO Auto-generated method stub
		
		try {
		String key=String.valueOf(order.getOrderId());
		orderRedis.put(key, order, -1);
		
		String userId=String.valueOf(order.getUser().getUserId());
		
		ArrayList<Integer> orderIdList= userOrderRedis.get(userId);

		orderIdList.add(order.getOrderId());
		userOrderRedis.put(userId, orderIdList, -1);
		
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	//根据用户id获取用户当日所有的委托
	@Override
	public List<Order> findByUserId(int userId){
		
		List<Order> orderList=new ArrayList<>();
		
		ArrayList<Integer> orderIdList=userOrderRedis.get(String.valueOf(userId));
		
		
		for(Integer orderId:orderIdList) {
			Order order=orderRedis.get(String.valueOf(orderId));
			orderList.add(order);
		}
		
		List<Order> dbOrderList=orderRepository.findByUser_UserId();
		
		orderList.addAll(dbOrderList);
		
		return orderList;
		
    }

	@Override
	public void addOrderToDb(Order order) {
		// TODO Auto-generated method stub
		
		orderRepository.saveAndFlush(order);
		
	}

	@Override
	public void updateOrderByTradeOrder(TransactionOrder transactionOrder) {
		// TODO Auto-generated method stub
		int OrderId=transactionOrder.getOrderId();
		
		Order order=orderRedis.get(String.valueOf(OrderId));
		
		orderRedis.remove(String.valueOf(OrderId));
		
		BeanUtils.copyProperties(transactionOrder, order);
		
		addOrderToDb(order);
		
		String userId=String.valueOf(transactionOrder.getOwnerId());
		
		ArrayList<Integer> orderIdList= userOrderRedis.get(userId);

		orderIdList.remove(order.getOrderId());
		userOrderRedis.put(userId, orderIdList, -1);
		
		
	}

	
	
}
