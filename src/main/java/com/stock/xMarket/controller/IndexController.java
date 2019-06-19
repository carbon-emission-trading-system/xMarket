package com.stock.xMarket.controller;

import static com.stock.xMarket.response.CommonReturnType.success;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.TimeShareVO;
import com.stock.xMarket.model.TimeShare;
import com.stock.xMarket.repository.TimeShareRepository;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.IndexService;





@RestController
public class IndexController extends BaseApiController {

	
	@Autowired
	TimeShareRepository timeShareRepository;
		
	
@Autowired
IndexService indexService;
	
@RequestMapping(value = "/getIndex")
public CommonReturnType getIndex() {
		
	return success(indexService.getIndex());
}
	

@RequestMapping(value = "/firstIndexTimeSharingDisplay")
public CommonReturnType firstTimeShareDisplay(@RequestParam String indexId) {
	
	Date nowDate=new Date(System.currentTimeMillis());
	
	List<TimeShareVO> timeShareVOList=new ArrayList<>();
	
	
	List<TimeShare> timeShareList=timeShareRepository.findByStockIdAndDateOrderByRealTime(indexId,nowDate);
	for(TimeShare timeShare:timeShareList) {
		TimeShareVO timeShareVO=new TimeShareVO();
		
		BeanUtils.copyProperties(timeShare, timeShareVO);
		Date date =timeShare.getDate();
		timeShareVO.setDate(date);
		timeShareVO.setStockId(indexId);
		timeShareVOList.add(timeShareVO);
	}
	
	return success(timeShareVOList);
	
	
	
}

//界面中某只个股点进去 展示个股的实时信息
		@RequestMapping(value = "/indexInfo")
		public CommonReturnType indexInfo(@RequestParam(value = "indexId") String indexId) {
			
			return success(indexService.indexInfo(indexId));
		}


	
	
}
