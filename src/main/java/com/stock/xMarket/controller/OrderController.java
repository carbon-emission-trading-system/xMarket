package com.stock.xMarket.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.OrderService;


import static com.stock.xMarket.response.CommonReturnType.*;

@RestController
public class OrderController extends BaseApiController{

	@Autowired
    private OrderService orderService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);
	 
	
	@RequestMapping(value = "/buyOrSale")
	public CommonReturnType buyOrSale(@ModelAttribute(value = "SentstockTrading")OrderVO orderVO,HttpSession session, String validateCode,HttpServletResponse response){
		
		
		
//		marchService.produceOrder();
//		orderService.saveOrder();
		return success();

	}
	
	
	
	
	
	//找到用户的所有今日委托单信息
    @RequestMapping(value = "/api/todayOrder/{id}", method = RequestMethod.GET)
    public CommonReturnType findAllTodayOrder(@PathVariable("id") int id) {
    	
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
