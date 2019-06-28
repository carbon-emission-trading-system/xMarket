package com.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

        Stock findByStockName(String stockName);

		Stock findByStockId(String stockId);
}



