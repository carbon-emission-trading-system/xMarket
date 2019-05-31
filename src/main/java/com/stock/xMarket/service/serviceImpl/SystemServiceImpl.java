package com.stock.xMarket.service.serviceImpl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.StockHistory;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.OrderRepository;
import com.stock.xMarket.repository.StockHistoryRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.service.MarchService;
import com.stock.xMarket.service.SystemService;




@Service
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private RealTimeRedis realTimeRedis;
	

	@Autowired
	private OrderRedis orderRedis;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private StockHistoryRepository stockHistoryRepository;
	
	
	@Autowired
	private HoldPositionRepository holdPositionRepository;
	

	@Autowired
	private MarchService marchService;
	

	@Autowired
	private UserFundRepository userFundRepository;
	
	
	@Override
	public void initialRealTime() {
		// TODO Auto-generated method stub

		List<Stock> stockList=new ArrayList<>();
		
		stockList=stockRepository.findAll();
		
		for(Stock stock:stockList) {
			String key=String.valueOf(stock.getStockId());
			RealTimeVO realTimeVO=new RealTimeVO(stock.getStockId());
			double ytdClosePrice=realTimeRedis.get(key).getLatestPrice();
			realTimeVO.setYtdClosePrice(ytdClosePrice);
			realTimeRedis.put(key, realTimeVO, -1);
			
			
		}
	}


	@Override
	public void addStockHistory() {
		
		List<Stock> stockList=new ArrayList<>();
		
		stockList=stockRepository.findAll();
		
		for(Stock stock:stockList) {
			String key=String.valueOf(stock.getStockId());
			RealTimeVO realTimeVO=realTimeRedis.get(key);
			StockHistory stockHistory=new StockHistory();
			BeanUtils.copyProperties(realTimeVO, stockHistory);
			
			double closePrice=realTimeVO.getLatestPrice();
			//保存其他信息
			stockHistory.setClosePrice(closePrice);
			
			stockHistoryRepository.saveAndFlush(stockHistory);
		}
		// TODO Auto-generated method stub
		
		// realTimeRedis
		
		
		
	}

	@Override
	public void cleanOrder() {
		// TODO Auto-generated method stub
		
		Map<String, Order> orderMap=orderRedis.getEntities();
		
		for (Entry<String, Order> entry :orderMap.entrySet()) {
	
			int orderId =Integer.parseInt(entry.getKey());
		
		marchService.cancel(orderId);
			
			
		}
	}


	@Override
	public void unfreeze() {
		// TODO Auto-generated method stub
		List<HoldPosition> holdPositionList=holdPositionRepository.findAll();
		for(HoldPosition holdPosition:holdPositionList) {
			int availableNumber=holdPosition.getPositionNumber();
			holdPosition.setAvailableNumber(availableNumber);
		}
	}
	
	
	
	


}
