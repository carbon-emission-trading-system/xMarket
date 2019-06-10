package com.stock.xMarket.service.serviceImpl;

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

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.StockHistory;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.OrderRepository;
import com.stock.xMarket.repository.StockHistoryRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.service.MarchService;
import com.stock.xMarket.service.SystemService;
import com.stock.xMarket.service.RealTimeService;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private RealTime2Redis realTime2Redis;

	@Autowired
	private RealTime1Redis realTime1Redis;

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
	private RealTimeService realTimeService;
	
	@Autowired
	private UserFundRepository userFundRepository;
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	@PostConstruct
	public void initialRealTime() {
		// TODO Auto-generated method stub

		List<Stock> stockList = new ArrayList<>();

		stockList = stockRepository.findAll();

		for (Stock stock : stockList) {
			String key = String.valueOf(stock.getStockId());
			RealTime2 realTime2 = new RealTime2(stock.getStockId());
			// double ytdClosePrice=realTimeRedis.get(key).getLatestPrice();
			// realTimeVO.setYesterdayClosePrice(ytdClosePrice);
			realTime2Redis.put(key, realTime2, -1);

		}
	}

	@Override
	public void addStockHistory() {

		List<Stock> stockList = new ArrayList<>();

		stockList = stockRepository.findAll();

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
			String key = String.valueOf(realTimeVO.getStockId());

			StockHistory stockHistory = new StockHistory();
			BeanUtils.copyProperties(realTimeVO, stockHistory);

			double closePrice = realTimeVO.getLatestPrice();
			// 保存其他信息
			stockHistory.setClosePrice(closePrice);

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
			int availableNumber = holdPosition.getPositionNumber();
			holdPosition.setAvailableNumber(availableNumber);
		}
	}

}
