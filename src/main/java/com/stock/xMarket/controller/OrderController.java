package com.stock.xMarket.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.result.Result;
import com.stock.xMarket.service.OrderService;

@RestController
public class OrderController {

	@Autowired
    private OrderService orderService;
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);
	 
	//找到用户的所有今日委托单信息
    @RequestMapping(value = "/api/todayOrder/{id}", method = RequestMethod.GET)
    public Result<List<OrderVO>> findAllTodayOrder(@PathVariable("id") int id) {
    	
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
    	return Result.success(userVOList);
    }



}
