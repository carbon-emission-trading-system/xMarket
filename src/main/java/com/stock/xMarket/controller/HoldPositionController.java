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
	
	@RequestMapping(value = "/holdPosition", method = RequestMethod.GET)
	public CommonReturnType findHoldPosition(@RequestParam("userId") int userId) {//
		
		List<HoldPositionVO> list = holdPositionService.findHoldPosition(userId);
		if(list!=null) {
			return CommonReturnType.success(list);
		}
		return CommonReturnType.success( );
		
		
	}

	    	
	

}
