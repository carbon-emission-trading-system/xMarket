package com.stock.xMarket.repository;


import com.stock.xMarket.model.HoldPosition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldPositionRepository extends JpaRepository<HoldPosition, Integer> {


	HoldPosition findByUser_UserIdAndStock_StockId(int userId, String stockId);

	List<HoldPosition> findByUser_UserId(int userId);
	
	
}
