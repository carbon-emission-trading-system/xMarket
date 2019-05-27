package com.stock.xMarket.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stock.xMarket.service.RealTimeService;



public class RealTimeAPIController extends BaseApiController{


	private static Logger log = LoggerFactory.getLogger(UserApiController.class);
	
	
	@Autowired
	RealTimeService realTimeService;
	
	
	@RequestMapping(value = "/realTimeDataDisplay")
	public String realTimeDataDisplay(HttpSession session, String validateCode,HttpServletResponse response) {
		
		
		
		return "首次拿到实时信息";
		
	}
	
	
}
