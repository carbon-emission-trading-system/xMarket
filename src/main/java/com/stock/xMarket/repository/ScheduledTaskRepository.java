package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.ScheduledTask;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Integer>{

	ScheduledTask findByTaskKey(String taskKey);
	
	
	
	
	
}
