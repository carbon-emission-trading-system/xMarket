package com.stock.xMarket.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.serviceImpl.NewsServiceImpl;

@RestController
public class NewsController {

	@Autowired
	private NewsServiceImpl newsService;
	
	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public CommonReturnType getNews() {
		
		return CommonReturnType.success(newsService.findAll());
		
		
	}
	
	
}
