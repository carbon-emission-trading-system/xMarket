package com.stock.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.repository.SelfSelectStockRepository;
import com.stock.xMarket.service.SelfSelectStockService;

@Service
@Transactional
public class SelfSelectStockServiceImpl implements SelfSelectStockService {

	
	@Autowired
	SelfSelectStockRepository selfSelectStockRepoditory;
	
	@Override
	public void isSelected() {
		// TODO Auto-generated method stub
		
		selfSelectStockRepoditory.findByUser_UserIdAndStock_StockId();
		
	}

}
