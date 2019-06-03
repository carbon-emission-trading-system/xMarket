package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.SelfSelectStock;

@Repository
public interface SelfSelectStockRepository extends JpaRepository<SelfSelectStock, Integer> {

	SelfSelectStock findByUser_UserIdAndStock_StockId();

}
