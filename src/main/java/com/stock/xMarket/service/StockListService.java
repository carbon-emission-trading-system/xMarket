package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;

public interface StockListService {
	
	//展示个股股票列表
	List<RealTime2> findRealTime2();
	List<RealTime1> findRealTime1();
	
	//根据用户id展示自选股列表
	List<RealTime1> findSelfSelectStockRealTime1(int userId) throws BusinessException;
	List<RealTime2> findSelfSelectStockRealTime2(int userId) throws BusinessException;
}
