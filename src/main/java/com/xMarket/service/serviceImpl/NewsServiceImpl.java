package com.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xMarket.model.News;
import com.xMarket.repository.NewsRepository;
import com.xMarket.service.NewsService;

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
	public News findByTitle(String title) {
		return newsRepository.findByTitle(title);
	}
	
}
