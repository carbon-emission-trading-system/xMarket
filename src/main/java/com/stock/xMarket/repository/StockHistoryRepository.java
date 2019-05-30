package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.StockHistory;




@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory,Integer>{

}
