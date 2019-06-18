
package com.stock.xMarket.service.serviceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.CallOrderRedis;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.redis.TransactionRedis;
import com.stock.xMarket.redis.UserOrderRedis;
import com.stock.xMarket.repository.OrderRepository;
import com.stock.xMarket.repository.StockRepository;
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
	private OrderRepository orderRepository;

	@Autowired
	private StockRepository stockRepository;

	/** The order redis. */
	@Autowired
	private OrderRedis orderRedis;

	@Autowired
	private UserOrderRedis userOrderRedis;

	@Autowired
	TransactionRedis transactionRedis;

	@Autowired
	private CallOrderRedis callOrderRedis;

	@Autowired
	RabbitTemplate rabbitTemplate;

	/**
	 * Adds the order to redis.
	 *
	 * @param order the order
	 */
	@Override
	public void addOrderToRedis(Order order) {
		// TODO Auto-generated method stub

		try {
			String key = String.valueOf(order.getOrderId());
			orderRedis.put(key, order, -1);

			String userId = String.valueOf(order.getUser().getUserId());

			ArrayList<String> orderIdList = new ArrayList<>();
			if (userOrderRedis.get(userId) != null)
				orderIdList = userOrderRedis.get(userId);

			orderIdList.add(String.valueOf(order.getOrderId()));
			userOrderRedis.put(userId, orderIdList, -1);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	// 根据用户id获取用户当日所有的委托
	@Override
	public List<Order> findByUserId(int userId) {

		List<Order> orderList = new ArrayList<>();

		ArrayList<String> orderIdList = userOrderRedis.get(String.valueOf(userId));

		if (orderIdList != null) {
			for (String orderId : orderIdList) {
				Order order = orderRedis.get(orderId);
				if (order != null) {
					TransactionOrder transactionOrder = transactionRedis.get(orderId);
					if (transactionOrder != null)
						BeanUtils.copyProperties(transactionOrder, order);
				}
				orderList.add(order);
			}
		}

		Date date = new Date(System.currentTimeMillis());

		List<Order> dbOrderList = orderRepository.findByUser_UserIdAndDateOrderByTimeDesc(userId, date);
		
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

		long OrderId = transactionOrder.getOrderId();
		Order order = orderRedis.get(String.valueOf(OrderId));
		if (order == null)
			order = new Order();
		else {
			orderRedis.remove(String.valueOf(OrderId));

		}
		Time time = order.getTime();

		BeanUtils.copyProperties(transactionOrder, order);

		order.setExchangeAveragePrice(transactionOrder.getTradePrice());
		order.setTime(time);


		order.setCancelNumber(transactionOrder.getCancelNumber());

		
		if(transactionOrder.getCancelNumber() > 0) {
			if( transactionOrder.getExchangeAmount() > 0) {
				order.setState(3);
			}else {
				order.setState(4);
			}
		}else {
			order.setState(2);
		}

		addOrderToDb(order);

		String userId = String.valueOf(transactionOrder.getOwnerId());

		ArrayList<String> orderIdList = userOrderRedis.get(userId);

		if (orderIdList == null)
			orderIdList = new ArrayList<>();

		try {
			orderIdList.remove(String.valueOf(order.getOrderId()));
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("Redis异常");
		}
		userOrderRedis.put(userId, orderIdList, -1);

	}

	@Override
	@Scheduled(cron = "0 00 00 ? * MON-FRI")
	public void sendCallOrder() {
		// TODO Auto-generated method stub
		List<Stock> stockList = stockRepository.findAll();
		for (Stock stock : stockList) {

			String stockId = String.valueOf(stock.getStockId());

			List<Order> orderList = callOrderRedis.get(stockId);
			if (!orderList.isEmpty()) {
				for (Order order : orderList) {
					rabbitTemplate.convertAndSend("callMarchExchange", "callMarch." + stockId,
							JSON.toJSONString(order));
					
					orderList.remove(order);
					
				}
			} else {

			}

		}
	}
	
	@Override
	public void sendCancelOrder(long orderId) throws BusinessException {
	
	Order order=orderRepository.findByOrderId(orderId);
    	
    	if(order==null) {
    		
    		order=orderRedis.get(String.valueOf(orderId));
    		if(order==null) 
    			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
    		
    		
    	}
    	
    	order.setState(1);
    	
    	String stockId = String.valueOf(order.getStock().getStockId());
    	
    	
    	ArrayList<Order> orderList = callOrderRedis.get(stockId);
    	if(orderList==null)
    		orderList=new ArrayList<>();
		if (!orderList.isEmpty()) {
			if(orderList.contains(order)) {
			orderList.remove(order);
			callOrderRedis.put(stockId, orderList, -1);
			}else {
			//	rabbitTemplate.convertAndSend("userOrderExchange", "cancelMarch." + stockId,
					//	orderId);
				rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey",
						orderId);
			}
			
		} else {
		
//			rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey" ,
//					orderId);
//			
			rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey",
					orderId);
			
		}

	}
	
    //创建StockTradeVO的方法
	@Override
    public StockTradeVO createStockTradeVO(UserFund userFund,RealTime1 realTime1,Stock stock,HoldPosition holdPosition) {
        StockTradeVO stockTradeVO = new StockTradeVO();
        stockTradeVO.setBalance(userFund.getBalance());
        stockTradeVO.setStockId(realTime1.getStockId());
        stockTradeVO.setOrderPrice(realTime1.getLastTradePrice());
        stockTradeVO.setTradeMarket(stock.getTradeMarket());
        stockTradeVO.setStockName(stock.getStockName());
        if (holdPosition == null){
            stockTradeVO.setAvailableNumber(0);
        }else {
            stockTradeVO.setAvailableNumber(holdPosition.getAvailableNumber());
        }
        stockTradeVO.setYesterdayClosePrice(realTime1.getYesterdayClosePrice());
        return stockTradeVO;
    }
	

}
