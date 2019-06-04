package com.stock.xMarket.controller;



import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.model.TimeShare;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.repository.TimeShareRepository;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.RealTimeService;
import com.stock.xMarket.service.SelfSelectStockService;


import static com.stock.xMarket.response.CommonReturnType.*;


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
	public CommonReturnType timeShareDisplay(@RequestParam Integer stockId) {
		
		Date date=new Date(System.currentTimeMillis());
		List<TimeShare> timeShareList=timeShareRepository.findByStock_StockIdAndDate(stockId,date);
		
		return success(timeShareList);
		
	}
	
	@RequestMapping(value = "/isSelfSelectStock")
	public CommonReturnType isSelfSelectStock(@RequestParam Integer stockId,@RequestParam Integer userId) {
		
		if(selfSelectStockService.isSelected(stockId,userId))
			return success(true);
		else
			return success(false);
		
	}
	
	//用户添加自选股
		@RequestMapping(value = "/api/addSelfSelectStock")
		public void addSelfSelectStockToDb(@RequestParam(value = "userId") int userId,@RequestParam(value = "stockId") int stockId) {
			SelfSelectStock sss = new SelfSelectStock();
			sss.setUserId(userId);
			sss.setStockId(stockId);
			selfSelectStockService.addSelfSelectStockToDb(sss);
		}
		
	//用户删除自选股
	@RequestMapping(value = "/api/deleteSelfSelectStock")
	public void deleteSelfSelectStock(@RequestParam(value = "userId") int userId,@RequestParam(value = "stockId") int stockId) {
		SelfSelectStock sss = selfSelectStockService.findByUserIdAndStockId(userId,stockId);
		selfSelectStockService.deleteSelfSelectStockFromDb(sss);
	}
	
}
