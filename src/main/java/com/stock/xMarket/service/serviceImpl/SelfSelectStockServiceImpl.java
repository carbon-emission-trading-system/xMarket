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
	public boolean isSelected(Integer stockId, Integer userId) {
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
	
}
