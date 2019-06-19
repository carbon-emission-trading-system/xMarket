package com.stock.xMarket.controller;

import static com.stock.xMarket.response.CommonReturnType.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.IndexService;






public class IndexController {

	
@Autowired
IndexService indexService;
	
@RequestMapping(value = "/getIndex")
public CommonReturnType getIndex() {
		
	return success(indexService.getIndex());
}
	





	
	
}
