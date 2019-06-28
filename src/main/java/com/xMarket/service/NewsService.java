package com.xMarket.service;

import java.util.List;

import com.xMarket.model.News;



public interface NewsService {

	List<News> findAll();

	News findByTitle(String title);
}
