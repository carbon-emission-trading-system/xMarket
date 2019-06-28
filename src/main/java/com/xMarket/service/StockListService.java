package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.StockListVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.RealTime1;
import com.xMarket.model.RealTime2;
import com.xMarket.model.Stock;

public interface StockListService {
	
	//展示个股股票列表
	List<RealTime2> findRealTime2();
	List<RealTime1> findRealTime1();
	
	//根据用户id展示自选股列表
	List<RealTime1> findSelfSelectStockRealTime1(int userId) throws BusinessException;
	List<RealTime2> findSelfSelectStockRealTime2(int userId) throws BusinessException;
	List<StockListVO> finalList(List<RealTime1> realTime1List, List<RealTime2> realTime2List,
			List<StockListVO> stockListVOList);
	List<Stock> findStcokList();
}
