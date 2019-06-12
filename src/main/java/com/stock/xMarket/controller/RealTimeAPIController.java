package com.stock.xMarket.controller;



import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.TimeShareVO;
import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.model.TimeShare;
import com.stock.xMarket.repository.TimeShareRepository;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.RealTimeService;
import com.stock.xMarket.service.SelfSelectStockService;


import static com.stock.xMarket.response.CommonReturnType.*;

@RestController
public class RealTimeAPIController extends BaseApiController{


	private static Logger log = LoggerFactory.getLogger(UserApiController.class);
	
	
	@Autowired
	RealTimeService realTimeService;
	
	@Autowired
	SelfSelectStockService selfSelectStockService;
	

	
	@Autowired
	TimeShareRepository timeShareRepository;
	
	
	@RequestMapping(value = "/realTimeDataDisplay")
	public String realTimeDataDisplay(@RequestParam Integer stockId) {
	
	
		
		return "首次拿到实时信息";
		
	}
	
	
	@RequestMapping(value = "/timeSharingDisplay")
	public CommonReturnType timeShareDisplay(@RequestParam Integer stockId) {
		
		Date nowDate=new Date(System.currentTimeMillis());
		
		List<TimeShareVO> timeShareVOList=new ArrayList<>();
		
		
		List<TimeShare> timeShareList=timeShareRepository.findByStock_StockIdAndDate(stockId,nowDate);
		for(TimeShare timeShare:timeShareList) {
			TimeShareVO timeShareVO=new TimeShareVO();
			
			BeanUtils.copyProperties(timeShare, timeShareVO);
			Date date =timeShareVO.getDate();
			timeShareVO.setDate(date);
			timeShareVO.setStockId(stockId);
			timeShareVOList.add(timeShareVO);
		}
		
		return success(timeShareVOList);
		
	}
	
	@RequestMapping(value = "/isSelfSelectStock")
	public CommonReturnType isSelfSelectStock(@RequestParam Integer stockId,@RequestParam Integer userId) {
		
		if(selfSelectStockService.isSelected(stockId,userId))
			return success(true);
		else
			return success(false);
		
	}
	
	//界面中某只个股点进去 展示个股的实时信息
	@RequestMapping(value = "/realTimeInfo")
	public CommonReturnType findRealTime(@RequestParam(value = "stockId") int stockId) {
		
		return success(realTimeService.findRealTime(stockId));
	}
	
	
	//用户添加自选股
	@RequestMapping(value = "/addSelfSelectStock",method = RequestMethod.POST)
	public CommonReturnType addSelfSelectStockToDb(@RequestParam(value = "userId") int userId,@RequestParam(value = "stockId") int stockId) {

		SelfSelectStock sss = new SelfSelectStock();
		sss.setUserId(userId);
		sss.setStockId(stockId);
		selfSelectStockService.addSelfSelectStockToDb(sss);
		return CommonReturnType.success();
	}
		
	//用户删除自选股
	@RequestMapping(value = "/deleteSelfSelectStock")
	public CommonReturnType deleteSelfSelectStock(@RequestParam(value = "userId") int userId,@RequestParam(value = "stockId") int stockId) {
		SelfSelectStock sss = selfSelectStockService.findByUserIdAndStockId(userId,stockId);
		selfSelectStockService.deleteSelfSelectStockFromDb(sss);
		return success();
	}
	
}
