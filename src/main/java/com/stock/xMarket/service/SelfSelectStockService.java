package com.stock.xMarket.service;

import com.stock.xMarket.model.SelfSelectStock;

public interface SelfSelectStockService {

	boolean isSelected(String stockId, Integer userId);

	//用户添加自选股
	void addSelfSelectStockToDb(SelfSelectStock sss);
	
	//根据用户的userId和stockId找到用户的某只自选股
	SelfSelectStock findByUserIdAndStockId(int userId,String stockId);
		
	//删除用户的某只自选股
	void deleteSelfSelectStockFromDb(SelfSelectStock sss);
}
