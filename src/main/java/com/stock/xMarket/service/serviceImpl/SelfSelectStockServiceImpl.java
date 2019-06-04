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
	SelfSelectStockRepository selfSelectStockRepoditory;
	
	@Override
	public boolean isSelected(Integer stockId, Integer userId) {
		// TODO Auto-generated method stub


		SelfSelectStock stock=selfSelectStockRepoditory.findByUserIdAndStockId(userId,stockId);
		if(stock==null) 
			return false;
		else
			return true;
			
		
		
		
	}
	
}
