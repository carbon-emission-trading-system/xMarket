package com.stock.xMarket.repository;

import com.stock.xMarket.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

        Stock findByStockName(String stockName);
}



