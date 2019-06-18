package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.StockListVO;

public interface RankingListService {

	
	public void rankingByDecrease(List<StockListVO> stockListVOList) ;
	
	public void rankingByExchangeAmount(List<StockListVO> stockListVOList) ;
	
	public void rankingByconversionHand(List<StockListVO> stockListVOList) ;

	public void rankingByIncrease(List<StockListVO> stockListVOList);
	
	
	
}
