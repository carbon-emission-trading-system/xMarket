package com.stock.xMarket.service;

import com.stock.xMarket.model.SelfSelectStock;

public interface SelfSelectStockService {

	boolean isSelected(Integer stockId, Integer userId);

	//用户添加自选股
	void addSelfSelectStockToDb(SelfSelectStock sss);
}
