package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.HistoryHoldPositionVO;

public interface HistoryHoldPositionService {
	
	List<HistoryHoldPositionVO> findByUserId(int userId);
}
