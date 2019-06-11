/*
 * <p>项目名称: xMarket </p>
 * <p>文件名称: OrderListener.java </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2019-5-31 </p>
 * <p>公司信息: ************公司 *********部</p>
 * @author <a href="mail to: *******@******.com" rel="nofollow">作者</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
package com.stock.xMarket.controller.listener;

import java.sql.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.MarchService;
import com.stock.xMarket.service.OrderService;
import com.stock.xMarket.service.UserFundService;
import com.stock.xMarket.util.UUIDUtil;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving order events. The class that is
 * interested in processing a order event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addOrderListener<code> method. When the order event occurs,
 * that object's appropriate method is invoked.
 *
 * @see OrderEvent
 */
@Controller
public class OrderListener {
	

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

	/** The order service. */
	@Autowired
	OrderService orderService;

	/** The hold position service. */
	@Autowired
	HoldPositionService holdPositionService;

	/** The user fund service. */
	@Autowired
	UserFundService userFundService;

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/** The stock repository. */
	@Autowired
	private StockRepository stockRepository;

	/** The march service. */
	@Autowired
	MarchService marchService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	/**
	 * Consume order.
	 *
	 * @param str the JSON
	 * @throws BusinessException
	 */
	@RabbitListener(queues = "orderQueue")
	public void consumeOrder(String str) throws BusinessException {
		try {
			logger.info("委托单监听器监听到消息: {} ", str);
		} catch (Exception e) {
			logger.error("委托单监听器监听发生异常：{} ", str, e.fillInStackTrace());
		}

		OrderVO orderVO = JSON.parseObject(str, OrderVO.class);
		Order order = new Order();

		try {
			int id=orderVO.getUserId();
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

		order.setTime(new Time(System.currentTimeMillis()));

		BeanUtils.copyProperties(orderVO, order);

		
		
		//生成id
		int orderId=(int) (System.currentTimeMillis()/1000);
		order.setOrderId(String.valueOf(orderId));
		orderVO.setOrderId(orderId);
		
		// 丢入撮合系统
		rabbitTemplate.convertAndSend("marchExchange", "marchRoutingKey", JSON.toJSONString(orderVO));
		
		// 将委托单添加至Redis
		try {
		orderService.addOrderToRedis(order);
		}catch (Exception e) {
			// TODO: handle exception
			logger.info("将委托单加入Redis发生异常");
		}

		
		if (orderVO.getType() == 1) {
			// 更新股票可用余额
			holdPositionService.updateHoldPositionByOrder(order);
		}else {
			// 更新个人资金
			userFundService.updateUserFundByOrder(order);
		}
	

		

	}

	@RabbitListener(queues = "cancelOrderQueue")
	public void consumeCancelOrder(int orderId) throws BusinessException {
		try {
			logger.info("委托单监听器监听到消息: {} ", orderId);
		} catch (Exception e) {
			logger.error("委托单监听器监听发生异常：{} ", orderId, e.fillInStackTrace());
		}

		marchService.cancel(orderId);


	}

}
