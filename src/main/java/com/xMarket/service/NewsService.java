package com.xMarket.service;

import java.util.List;

import com.xMarket.model.News;
import org.springframework.data.domain.Sort;



public interface NewsService {

	List<News> findAll(Sort sort);

	News findByTitle(String title);
}
