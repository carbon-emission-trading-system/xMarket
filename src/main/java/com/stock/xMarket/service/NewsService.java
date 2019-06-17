package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.model.News;



public interface NewsService {

	List<News> findAll();

	News findByName(String title);
}
