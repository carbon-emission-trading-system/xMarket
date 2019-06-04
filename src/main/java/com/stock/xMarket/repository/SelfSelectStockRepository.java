package com.stock.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.model.User;

@Repository
public interface SelfSelectStockRepository extends JpaRepository<SelfSelectStock,Integer> {
	
	List<SelfSelectStock> findByUserId(int userId);
	
	SelfSelectStock findByUser_UserIdAndStock_StockId();
}
