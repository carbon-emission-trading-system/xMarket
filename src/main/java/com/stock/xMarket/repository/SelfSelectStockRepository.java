package com.stock.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.SelfSelectStock;

@Repository
public interface SelfSelectStockRepository extends JpaRepository<SelfSelectStock,Integer> {
	
	List<SelfSelectStock> findByUserId(int userId);
}
