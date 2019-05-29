package com.stock.xMarket.repository;


import com.xmarket.order.model.HoldPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldPositionRepository extends JpaRepository<HoldPosition, Integer> {


	HoldPosition findByUser_UserIdAndStock_StockId(int userId, int stockId);


	
	
}
