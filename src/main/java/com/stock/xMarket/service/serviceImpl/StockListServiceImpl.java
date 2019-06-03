package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.repository.SelfSelectStockRepository;
import com.stock.xMarket.service.StockListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class StockListServiceImpl implements StockListService {

	@Autowired
	private RealTime2Redis realTime2Redis;
	@Autowired
	private RealTime1Redis realTime1Redis;
	@Autowired
	private SelfSelectStockRepository selfSelectStockRepository;
	
	//展示个股股票列表
	@Override
	public List<RealTime2> findRealTime2(){
		
		List<RealTime2> list = new ArrayList<>();	
		list = realTime2Redis.getAll();		
		return list;
	}
	
	//展示个股股票列表
	@Override
	public List<RealTime1> findRealTime1(){
		
		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();
		return list1;
	}
	
	//根据用户id，展示自选股列表
	@Override
	public List<RealTime1> findSelfSelectStockRealTime1(int userId){
		
		List<SelfSelectStock> selfSelectStockList = new ArrayList<>();
		selfSelectStockList = selfSelectStockRepository.findByUserId(userId);
		
		List<RealTime1> realTime1List = new ArrayList<>();
		for(SelfSelectStock s : selfSelectStockList) {
			
			realTime1List.add( realTime1Redis.get(String.valueOf(s.getStockId())) );
		}
		
		return realTime1List;
	}
	
	@Override
	public List<RealTime2> findSelfSelectStockRealTime2(int userId){
		List<SelfSelectStock> selfSelectStockList = new ArrayList<>();
		selfSelectStockList = selfSelectStockRepository.findByUserId(userId);
		
		List<RealTime2> realTime2List = new ArrayList<>();
		
		for(SelfSelectStock s : selfSelectStockList) {
			realTime2List.add( realTime2Redis.get(String.valueOf(s.getStockId())) );
		}
		
		return realTime2List;
	}
	
}
