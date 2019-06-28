package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.KLineDataVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.StockHistory;

public interface KlineService {

	 public List<KLineDataVO> createDayKLineDataVOList(String stockId) throws BusinessException;
	
}
