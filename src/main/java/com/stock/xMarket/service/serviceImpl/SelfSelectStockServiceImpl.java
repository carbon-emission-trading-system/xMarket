package com.stock.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.repository.SelfSelectStockRepository;
import com.stock.xMarket.service.SelfSelectStockService;

@Service
@Transactional
public class SelfSelectStockServiceImpl implements SelfSelectStockService {

	
	@Autowired
	SelfSelectStockRepository selfSelectStockRepository;
	
	@Override
	public boolean isSelected(String stockId, Integer userId) {
		// TODO Auto-generated method stub


		SelfSelectStock stock=selfSelectStockRepository.findByUserIdAndStockId(userId,stockId);
		if(stock==null) 
			return false;
		else
			return true;
			
		
		
		
	}
	
	//用户添加自选股
	@Override
	public void addSelfSelectStockToDb(SelfSelectStock sss) {
		selfSelectStockRepository.saveAndFlush(sss);
	}
	
	//找到用户某只自选股
	@Override
	public SelfSelectStock findByUserIdAndStockId(int userId,String stockId) {
		// TODO Auto-generated method stub
		return selfSelectStockRepository.findByUserIdAndStockId(userId, stockId);
	}
		
	//根据用户的userId和stockId删除用户的某只自选股
	@Override
	public void deleteSelfSelectStockFromDb(SelfSelectStock sss) {
		// TODO Auto-generated method stub
		selfSelectStockRepository.delete(sss);
	}
	
}
