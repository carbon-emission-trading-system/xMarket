package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.KLineDataVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.StockHistory;

public interface KlineService {

	 public List<KLineDataVO> createDayKLineDataVOList(List<StockHistory> stockHistoriesList) throws BusinessException;
	
}
