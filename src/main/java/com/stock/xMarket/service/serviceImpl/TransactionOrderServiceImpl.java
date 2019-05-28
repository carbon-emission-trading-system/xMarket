package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.TransactionOrderProjection;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.service.TransactionOrderService;

@Service
@Transactional
public class TransactionOrderServiceImpl implements TransactionOrderService {

	@Autowired
    private TransactionOrderRepository transactionOrderRepository ;
	
	@Override
	public List<TransactionOrderProjection> findByOwnerId(int ownerId){
		
		
		return transactionOrderRepository.findByOwnerId(ownerId);
    }
}
