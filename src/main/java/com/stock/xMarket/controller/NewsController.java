package com.stock.xMarket.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.NewsVO;
import com.stock.xMarket.model.News;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.serviceImpl.NewsServiceImpl;

@RestController
public class NewsController {

	@Autowired
	private NewsServiceImpl newsService;
	
	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public CommonReturnType getNews() {
		
		List<NewsVO> newsVOList = new ArrayList<NewsVO>();
		NewsVO newsVO = new NewsVO();
		
		for(News news : newsService.findAll()) {			
			BeanUtils.copyProperties(news, newsVO);
			newsVOList.add(newsVO);			
		}
		
		return CommonReturnType.success(newsVOList);		
	}
	
	@RequestMapping(value = "/getOneNews",method = RequestMethod.GET)
	public CommonReturnType getOneNews(@RequestParam("name") String name) {
		
		NewsVO newsVO = new NewsVO();
		BeanUtils.copyProperties(newsService.findByName(name), newsVO);
		return CommonReturnType.success(newsVO); 
	}
	
	
}
