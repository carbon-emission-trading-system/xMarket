package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.HistoryHoldPositionVO;

public interface HistoryHoldPositionService {
	
	List<HistoryHoldPositionVO> findByUserId(int userId);
}
