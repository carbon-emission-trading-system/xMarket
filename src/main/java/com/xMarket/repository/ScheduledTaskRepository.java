package com.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xMarket.model.Order;
import com.xMarket.model.ScheduledTask;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Integer>{

	ScheduledTask findByTaskKey(String taskKey);
	
	
	
	
	
}
