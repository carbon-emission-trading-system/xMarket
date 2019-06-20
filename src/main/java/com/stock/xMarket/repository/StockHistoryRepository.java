package com.stock.xMarket.repository;

import com.sun.xml.internal.xsom.impl.scd.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.StockHistory;

import java.sql.Date;
import java.util.List;


@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory,Integer>{
        List<StockHistory> findAllByStockIdOrderByDate(String stockId);
        StockHistory findByStockIdAndDate(String stockId,Date date);
}
