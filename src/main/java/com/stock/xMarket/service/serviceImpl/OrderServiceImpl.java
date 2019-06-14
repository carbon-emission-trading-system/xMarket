
package com.stock.xMarket.service.serviceImpl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.redis.TransactionRedis;
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
	
	@Autowired
	TransactionRedis transactionRedis;
	

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
		
		ArrayList<String> orderIdList= new ArrayList<>();
		if(userOrderRedis.get(userId)!=null)
			orderIdList=userOrderRedis.get(userId);

		orderIdList.add(String.valueOf(order.getOrderId()));
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
		
		ArrayList<String> orderIdList=userOrderRedis.get(String.valueOf(userId));
		
		if(orderIdList!=null) {
		for(String orderId:orderIdList) {
			Order order=orderRedis.get(orderId);
			if(order!=null) {
			TransactionOrder transactionOrder=transactionRedis.get(orderId);
			if(transactionOrder!=null)
				BeanUtils.copyProperties(transactionOrder, order);
			}
			orderList.add(order);
		}
		}

		List<Order> dbOrderList=orderRepository.findByUser_UserId(userId);
		

		orderList.addAll(dbOrderList);
		
		return orderList;
		
    }

	@Override
	public void addOrderToDb(Order order) {
		// TODO Auto-generated method stub
		
		orderRepository.saveAndFlush(order);
		
	}

	@Override
	public void updateOrderBytransactionOrder(TransactionOrder transactionOrder) {
		// TODO Auto-generated method stub
		
		long OrderId=transactionOrder.getOrderId();
		Order order=orderRedis.get(String.valueOf(OrderId));
		if(order==null)
			order=new Order();
		else {
			orderRedis.remove(String.valueOf(OrderId));
		
		}
		Time time=order.getTime();
		
		BeanUtils.copyProperties(transactionOrder, order);
		
		order.setExchangeAveragePrice(transactionOrder.getTradePrice());
		order.setOrderId(transactionOrder.getOrderId());
		order.setTime(time);
		order.setCancelNumber(transactionOrder.getRevokeAmount());
		
		addOrderToDb(order);
		
		String userId=String.valueOf(transactionOrder.getOwnerId());
		
		ArrayList<String> orderIdList= userOrderRedis.get(userId);
		
		if(orderIdList==null)
			orderIdList=new ArrayList<>();

		try {
		orderIdList.remove(String.valueOf(order.getOrderId()));
		}catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("Redis异常");
		}
		userOrderRedis.put(userId, orderIdList, -1);
		
		}
		
	

	
	
}
