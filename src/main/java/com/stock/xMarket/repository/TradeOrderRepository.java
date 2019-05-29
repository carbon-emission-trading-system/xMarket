package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.TradeOrder;



@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, String>{
	
	

}