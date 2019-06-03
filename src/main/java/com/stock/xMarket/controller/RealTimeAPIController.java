package com.stock.xMarket.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.repository.TimeShareRepository;
import com.stock.xMarket.result.Result;
import com.stock.xMarket.service.RealTimeService;
import com.stock.xMarket.service.SelfSelectStockService;



public class RealTimeAPIController extends BaseApiController{


	private static Logger log = LoggerFactory.getLogger(UserApiController.class);
	
	
	@Autowired
	RealTimeService realTimeService;
	
	@Autowired
	SelfSelectStockService selfSelectStockService;
	
	@Autowired
	RealTimeRedis realTimeRedis;
	
	@Autowired
	TimeShareRepository timeShareRepository;
	
	@RequestMapping(value = "/realTimeDataDisplay")
	public String realTimeDataDisplay(@RequestParam Integer stockId) {
		
		
		return "首次拿到实时信息";
		
	}
	
	
	@RequestMapping(value = "/timeShareDisplay")
	public String timeShareDisplay(@RequestParam Integer stockId) {
		
		//timeShareRepository.findByStock_StockIdAndDate
		
		return "首次拿到实时信息";
		
	}
	
	@RequestMapping(value = "/isSelfSelectStock")
	public Result<Boolean> isSelfSelectStock(@RequestParam Integer stockId,@RequestParam Integer userId) {
		
		if(selfSelectStockService.isSelected(stockId,userId))
			return Result.success(true);
		else
			return Result.success(false);
		
	}
	
	
	
	
}
