//package com.xMarket.controller.listener;
//
//
//import java.sql.Time;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import com.alibaba.fastjson.JSON;
//import com.xMarket.VO.OrderVO;
//import com.xMarket.error.BusinessException;
//import com.xMarket.error.EmBusinessError;
//import com.xMarket.model.Order;
//import com.xMarket.model.Stock;
//import com.xMarket.model.User;
//import com.xMarket.repository.StockRepository;
//import com.xMarket.repository.UserRepository;
//import com.xMarket.service.HoldPositionService;
//import com.xMarket.service.OrderService;
//import com.xMarket.service.UserFundService;
//import com.xMarket.util.UUIDUtil;
//
//// TODO: Auto-generated Javadoc
///**
// * The listener interface for receiving order events. The class that is
// * interested in processing a order event implements this interface, and the
// * object created with that class is registered with a component using the
// * component's <code>addOrderListener<code> method. When the order event occurs,
// * that object's appropriate method is invoked.
// *
// * @see OrderEvent
// */
//@Controller
//public class OrderListener {
//	
//
//	/** The Constant logger. */
//	private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);
//
//	/** The order service. */
//	@Autowired
//	OrderService orderService;
//
//	/** The hold position service. */
//	@Autowired
//	HoldPositionService holdPositionService;
//
//	/** The user fund service. */
//	@Autowired
//	UserFundService userFundService;
//
//	/** The user repository. */
//	@Autowired
//	UserRepository userRepository;
//
//	/** The stock repository. */
//	@Autowired
//	private StockRepository stockRepository;
//
//	
//	@Autowired
//	RabbitTemplate rabbitTemplate;
//	
//	/**
//	 * Consume order.
//	 *
//	 * @param str the JSON
//	 * @throws BusinessException
//	 */
//	@RabbitListener(queues = "orderQueue")
//	public void consumeOrder(String str) throws BusinessException {
//		try {
//			logger.info("委托单监听器监听到消息: {} ", str);
//		} catch (Exception e) {
//			logger.error("委托单监听器监听发生异常：{} ", str, e.fillInStackTrace());
//		}
//
//		OrderVO orderVO = JSON.parseObject(str, OrderVO.class);
//		orderService.buyOrSale(orderVO);
//
//	}
//
//
//}
