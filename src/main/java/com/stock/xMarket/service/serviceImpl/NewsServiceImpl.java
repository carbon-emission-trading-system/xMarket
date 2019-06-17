package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.model.News;
import com.stock.xMarket.repository.NewsRepository;
import com.stock.xMarket.service.NewsService;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsRepository newsRepository;

	@Override
	public List<News> findAll() {
		List<News> list = new ArrayList<>();
		list = newsRepository.findAll();
		return list;
	}
	
	@Override 
	public News findByName(String title) {
		return newsRepository.findByName(title);
	}
	
}
