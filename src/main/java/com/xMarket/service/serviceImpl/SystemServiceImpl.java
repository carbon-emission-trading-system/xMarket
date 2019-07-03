package com.xMarket.service.serviceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;


import com.alibaba.fastjson.JSON;
import com.xMarket.VO.IndexVO;
import com.xMarket.VO.RealTimeVO;
import com.xMarket.VO.UserFundVO;
import com.xMarket.model.*;
import com.xMarket.redis.IndexRedis;
import com.xMarket.redis.OrderRedis;
import com.xMarket.redis.RealTime1Redis;
import com.xMarket.redis.RealTime2Redis;
import com.xMarket.redis.TimeShareRedis;
import com.xMarket.repository.*;
import com.xMarket.service.HoldPositionService;
import com.xMarket.service.RealTimeService;
import com.xMarket.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RealTime2Redis realTime2Redis;

	@Autowired
	private RealTime1Redis realTime1Redis;
	
	@Autowired
	private TimeShareRedis timeShareRedis;

	@Autowired
	private IndexRedis indexRedis;
	
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
	private UserFundHistoryRepository userFundHistoryRepository;

	@Autowired
	private RealTimeService realTimeService;
	
	@Autowired
	private IndexRepository indexRepository;

	@Autowired
	private HoldPositionService holdPositionService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	@PostConstruct
	public void initialRealTime() {
		// TODO Auto-generated method stub

		List<Stock> stockList = new ArrayList<>();

		stockList = stockRepository.findAll();

		for (Stock stock : stockList) {
			String key = stock.getStockId();
			RealTime2 realTime2 = new RealTime2(stock);
			// double ytdClosePrice=realTimeRedis.get(key).getLatestPrice();
			// realTimeVO.setYesterdayClosePrice(ytdClosePrice);
			realTime2Redis.put(key, realTime2, -1);
			timeShareRedis.put(key, 0, -1);

		}
		
		
		List<Index> indexList = new ArrayList<>();

		indexList = indexRepository.findAll();
		
		for (Index index : indexList) {
			String key = index.getIndexId();
		
			IndexVO indexVO=new IndexVO(index);
			
			indexRedis.put(key,indexVO, -1);
			timeShareRedis.put(key, 0, -1);
		}
	}
	
	@Override
	@Scheduled(cron = "0 00 00 * * ?")
	public void clear() {
		cleanOrder();
		unfreeze();
		addStockHistory();
		addUserFundHistory();
		initialRealTime();
		
	}
	
	

	@Override
	public void addStockHistory() {

		// updateRealTime();
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		// redis 中读取数据
		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();

		List<RealTime2> list2 = new ArrayList<>();
		list2 = realTime2Redis.getAll();

		realTimeList = realTimeService.finalRealTime(list1, list2);
		// TODO Auto-generated method stub

		for (RealTimeVO realTimeVO : realTimeList) {
			StockHistory stockHistory = new StockHistory();
			BeanUtils.copyProperties(realTimeVO, stockHistory);
			stockHistoryRepository.saveAndFlush(stockHistory);
		}
		// TODO Auto-generated method stub

		// realTimeRedis

	}

	

	@Override
	public void cleanOrder() {
		// TODO Auto-generated method stub

		Map<String, Order> orderMap = orderRedis.getEntities();

		for (Entry<String, Order> entry : orderMap.entrySet()) {

			int orderId = Integer.parseInt(entry.getKey());

			rabbitTemplate.convertAndSend("orderExchange", "cancelOrderRoutingKey", orderId);

		}
	}

	@Override
	public void unfreeze() {
		// TODO Auto-generated method stub
		List<HoldPosition> holdPositionList = holdPositionRepository.findAll();
		for (HoldPosition holdPosition : holdPositionList) {
			long availableNumber = holdPosition.getPositionNumber();
			holdPosition.setAvailableNumber(availableNumber);
		}
	}

	@Override
	public void addUserFundHistory() {
		List<User> userList = userRepository.findAll();
		Date date = new Date(System.currentTimeMillis());
		double todaySZZS = indexRedis.get("100000").getLastIndex();
		double todaySZCZ = indexRedis.get("399001").getLastIndex();
		for (User user:userList){
			UserFundVO userFundVO = holdPositionService.getFunds(user.getUserId());
			double historySZZS = stockHistoryRepository.findByStockIdAndDate("100000",user.getRegisterDate()).getClosePrice();
			double historySZCZ = stockHistoryRepository.findByStockIdAndDate("399001",user.getRegisterDate()).getClosePrice();
			double userStartFund = 1000000;
			UserFundHistory userFundHistory = new UserFundHistory();
			userFundHistory.setUserId(user.getUserId());
			userFundHistory.setDate(date);
			userFundHistory.setTotalFunds(userFundVO.getTotalFunds());
			userFundHistory.setFundIncrease((userFundVO.getTotalFunds()-userStartFund)/userStartFund);
			userFundHistory.setSZZS((todaySZZS-historySZZS)/historySZZS);
			userFundHistory.setSZCZ((todaySZCZ-historySZCZ)/historySZCZ);
			userFundHistoryRepository.saveAndFlush(userFundHistory);
		}

	}

	
	
	
}
