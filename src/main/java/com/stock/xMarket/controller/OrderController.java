package com.stock.xMarket.controller;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.OrderService;
import com.stock.xMarket.service.UserFundService;
import com.stock.xMarket.util.OpeningUtil;

import static com.stock.xMarket.response.CommonReturnType.*;

@RestController
public class OrderController extends BaseApiController{

	@Autowired
    private OrderService orderService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	

	/** The user repository. */
	@Autowired
	UserRepository userRepository;
	

	/** The stock repository. */
	@Autowired
	private StockRepository stockRepository;
	
	/** The hold position service. */
	@Autowired
	HoldPositionService holdPositionService;

	/** The user fund service. */
	@Autowired
	UserFundService userFundService;

	
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);
	 
	
	@RequestMapping(value = "/buyOrSale")
	public CommonReturnType buyOrSale(@ModelAttribute(value = "SentstockTrading")OrderVO orderVO,HttpSession session, String validateCode,HttpServletResponse response) throws BusinessException{
		

		orderVO.setTime(new Time(System.currentTimeMillis()));
		orderVO.setDate(new Date(System.currentTimeMillis()));
		
		Order order = new Order();
		
		//生成id
		long orderId= Long.valueOf(String.valueOf(String.valueOf(orderVO.getUserId()+System.currentTimeMillis())));
		order.setOrderId(orderId);
		orderVO.setOrderId(orderId);

		
		
		BeanUtils.copyProperties(orderVO, order);

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

		if (orderVO.getType() == 1) {
			// 更新股票可用余额
			holdPositionService.updateHoldPositionByOrder(order);
		}else {
			// 更新个人资金
			userFundService.updateUserFundByOrder(order);
		}



		
		// 将委托单添加至Redis
				try {
				orderService.addOrderToRedis(order);
				}catch (Exception e) {
					// TODO: handle exception
					logger.info("将委托单加入Redis发生异常");
					throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"将委托单加入Redis发生异常");
				}

		

				if(OpeningUtil.isSet(order.getTime())) {
					rabbitTemplate.convertAndSend("marchExchange", "marchRoutingKey", JSON.toJSONString(orderVO));
				}else {
					rabbitTemplate.convertAndSend("marchExchange", "marchRoutingKey", JSON.toJSONString(orderVO));
				}

		
		return success();

	}
	
	
	
	
	
	//找到用户的所有今日委托单信息
    @RequestMapping(value = "/todayOrder", method = RequestMethod.GET)
    public CommonReturnType findAllTodayOrder(@RequestParam("userId") int id) {
    	
    	logger.info("传进来的用户ownerId："+id);
    	List<Order> list=orderService.findByUserId(id);
    	List<OrderVO> userVOList=new ArrayList<>();
    	
    	for(Order order : list) {
    		OrderVO orderVO=new OrderVO();
    		BeanUtils.copyProperties(order, orderVO);
    		orderVO.setStockId(order.getStock().getStockId());
			orderVO.setStockName(order.getStock().getStockName());
    		userVOList.add(orderVO);
    	}
    	
    	logger.info("传出去的结果："+userVOList);
    	return success(userVOList);
    }



}
