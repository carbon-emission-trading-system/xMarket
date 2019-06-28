package com.xMarket.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.News;

@Repository
public interface NewsRepository extends JpaRepository<News,Integer> {

	List<News> findAll();
	
	News findByTitle(String title);
}
