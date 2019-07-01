package com.xMarket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xMarket.VO.NewsVO;
import com.xMarket.VO.OneNewsVO;
import com.xMarket.error.BusinessException;
import com.xMarket.error.CommonError;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.News;
import com.xMarket.response.CommonReturnType;
import com.xMarket.service.serviceImpl.NewsServiceImpl;

@RestController
public class NewsController extends BaseApiController {

	@Autowired
	private NewsServiceImpl newsService;

	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public CommonReturnType getNews() {

		Sort sort = new Sort(Sort.Direction.DESC, "date");
		List<NewsVO> newsVOList = new ArrayList<NewsVO>();
		List<News> newsList = newsService.findAll(sort);
		if (newsList != null)
			for (News news : newsList) {
				NewsVO newsVO = new NewsVO();
				BeanUtils.copyProperties(news, newsVO);
				newsVOList.add(newsVO);
			}

		return CommonReturnType.success(newsVOList);
	}

	@RequestMapping(value = "/getOneNews", method = RequestMethod.GET)
	public CommonReturnType getOneNews(@RequestParam("title") String title) {

		OneNewsVO oneNewsVO = new OneNewsVO();
		BeanUtils.copyProperties(newsService.findByTitle(title), oneNewsVO);
		return CommonReturnType.success(oneNewsVO);

	}

}
