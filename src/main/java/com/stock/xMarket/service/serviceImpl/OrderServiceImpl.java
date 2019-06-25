
package com.stock.xMarket.service.serviceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.stock.xMarket.model.*;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.UserFundService;
import com.stock.xMarket.util.OpeningUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
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

	private static Logger logger = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

	/** The order repository. */
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private UserRepository userRepository;

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
	private HoldPositionService holdPositionService;

	@Autowired
	private UserFundService userFundService;

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

		Date date = new Date(System.currentTimeMillis());

		List<Order> orderList = new ArrayList<>();

		ArrayList<String> orderIdList = userOrderRedis.get(String.valueOf(userId));

		if (orderIdList != null) {
			for (String orderId : orderIdList) {
				Order order = orderRedis.get(orderId);
				if (order.getDate().equals(date)) {
					if (order != null) {
						TransactionOrder transactionOrder = transactionRedis.get(orderId);
						if (transactionOrder != null)
							BeanUtils.copyProperties(transactionOrder, order);
					}
					orderList.add(order);
				}
			}
		}

		List<Order> dbOrderList = orderRepository.findByUser_UserIdAndDateOrderByTimeDesc(userId, date);

		if (dbOrderList != null)
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

		if (transactionOrder.getCancelNumber() > 0) {
			if (transactionOrder.getExchangeAmount() > 0) {
				order.setState(3);
			} else {
				order.setState(4);
			}
		} else {
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

		Order order = orderRepository.findByOrderId(orderId);

		if (order == null) {

			order = orderRedis.get(String.valueOf(orderId));
			if (order == null)
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);

		}

		order.setState(1);

		String stockId = String.valueOf(order.getStock().getStockId());

		ArrayList<Order> orderList = callOrderRedis.get(stockId);
		if (orderList == null)
			orderList = new ArrayList<>();
		if (!orderList.isEmpty()) {
			if (orderList.contains(order)) {
				orderList.remove(order);
				callOrderRedis.put(stockId, orderList, -1);
			} else {
				// rabbitTemplate.convertAndSend("userOrderExchange", "cancelMarch." + stockId,
				// orderId);
				rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey", orderId);
			}

		} else {

//			rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey" ,
//					orderId);
//			
			rabbitTemplate.convertAndSend("userOrderExchange", "cancelOrderRoutingKey", orderId);

		}

	}

	// 创建StockTradeVO的方法
	@Override
	public StockTradeVO createStockTradeVO(UserFund userFund, RealTime1 realTime1, Stock stock,
			HoldPosition holdPosition) {
		StockTradeVO stockTradeVO = new StockTradeVO();
		stockTradeVO.setBalance(userFund.getBalance());
		stockTradeVO.setStockId(realTime1.getStockId());
		stockTradeVO.setOrderPrice(realTime1.getLastTradePrice());
		stockTradeVO.setTradeMarket(stock.getTradeMarket());
		stockTradeVO.setStockName(stock.getStockName());
		if (holdPosition == null) {
			stockTradeVO.setAvailableNumber(0);
		} else {
			stockTradeVO.setAvailableNumber(holdPosition.getAvailableNumber());
		}
		stockTradeVO.setYesterdayClosePrice(realTime1.getYesterdayClosePrice());
		return stockTradeVO;
	}

	@Override

	public void buyOrSale(OrderVO orderVO) throws BusinessException {

		Order order = generateOrder(orderVO);

		execOrder(order, orderVO);

	}

	@Async
	private void execOrder(Order order, OrderVO orderVO) throws BusinessException {
		// TODO Auto-generated method stub

		if (order.getType() == 1) {
			// 更新股票可用余额
			holdPositionService.updateHoldPositionByOrder(order);
		} else {
			// 更新个人资金
			userFundService.updateUserFundByOrder(order);
		}

		// 将委托单添加至Redis
		try {
			addOrderToRedis(order);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("将委托单加入Redis发生异常");
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "将委托单加入Redis发生异常");
		}

		try {
			sendOrder(orderVO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("委托單撮合发生异常");
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "委托單撮合发生异常发生异常");
		}

	}

	private void sendOrder(OrderVO orderVO) {
		// TODO Auto-generated method stub
		if (OpeningUtil.isSet(orderVO.getTime())) {
			// 集合竞价单，缓存到redis中
			rabbitTemplate.convertAndSend("marchExchange", "marchRoutingKey", JSON.toJSONString(orderVO));

//					String stockId = String.valueOf(order.getStock().getStockId());
//
//					ArrayList<Order> orderList = new ArrayList<>();
//
//						if(	callOrderRedis.get(stockId)!=null)
//							orderList=callOrderRedis.get(stockId);
//
//						orderList.add(order);
//						callOrderRedis.put(stockId, orderList, -1);

		} else {
			rabbitTemplate.convertAndSend("marchExchange", "marchRoutingKey", JSON.toJSONString(orderVO));
		}
	}

	private Order generateOrder(OrderVO orderVO) throws BusinessException {
		// TODO Auto-generated method stub
		orderVO.setTime(new Time(System.currentTimeMillis()));
		orderVO.setDate(new Date(System.currentTimeMillis()));
		Order order = new Order();

		// 生成id
		long orderId = Long.valueOf(String.valueOf(String.valueOf(orderVO.getUserId() + System.currentTimeMillis())));
		orderVO.setOrderId(orderId);

		BeanUtils.copyProperties(orderVO, order);

		try {
			int id = orderVO.getUserId();
			User user = userRepository.findByUserId(id);
			order.setUser(user);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标用户不存在！");
		}

		try {
			Stock stock = stockRepository.findByStockId(orderVO.getStockId());
			order.setStock(stock);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标股票不存在！");
		}

		return order;
	}
}
