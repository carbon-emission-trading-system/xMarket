package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.TimeShare;

@Repository
public interface TimeShareRepository extends JpaRepository<TimeShare,Integer>{

	
}
