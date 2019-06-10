package com.stock.xMarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.HoldPositionVO;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.HoldPositionService;


@RestController
public class HoldPositionController extends BaseApiController{
	
	@Autowired
	private HoldPositionService holdPositionService;
	
	//向前端返回用户的持仓股票
	@RequestMapping(value = "/presentHoldPositionInfo", method = RequestMethod.GET)
	public CommonReturnType findHoldPosition(@RequestParam("userId") int userId) {//
		
		List<HoldPositionVO> list = holdPositionService.findHoldPosition(userId);
		if(list!=null) {
			return CommonReturnType.success(list);
		}
		return CommonReturnType.success( );
		
		
	}
	
	//像前端返回用户的资产信息
	@RequestMapping(value = "/getFunds", method = RequestMethod.GET)
	public CommonReturnType getFunds(@RequestParam("userId") int userId) {//
	
		return CommonReturnType.success(holdPositionService.getFunds(userId));
	}

	    	
	

}
