package com.stock.xMarket.service.serviceImpl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.VO.TimeShareVO;
import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.model.TimeShare;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.repository.TimeShareRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.RealTimeService;
import com.stock.xMarket.service.TimeShareService;

@Service
//@Transactional
public class TimeShareServiceImpl implements TimeShareService {
	
	@Autowired
	private RealTime2Redis realTime2Redis;

	@Autowired
	private RealTime1Redis realTime1Redis;
	
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Autowired
	RabbitMqConfig rabbitmqConfig;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RealTimeService realTimeService;
	
	@Autowired
	public TimeShareRepository timeShareRepository;
	

	public static final String ALL_REALTIME_REDIS="allRealTimeRedis";
	
	@Override
	@Scheduled(fixedRate = 600000)
	public void sendTimeShare() {
		List<TimeShareVO> timeShareList = new ArrayList<>();
		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();

		List<RealTime2> list2 = new ArrayList<>();
		list2 = realTime2Redis.getAll();

		
		List<RealTimeVO> realTimeList = realTimeService.finalRealTime(list1, list2);
		// TODO Auto-generated method stub
		for(RealTimeVO realTime:realTimeList) {
			int stockID=realTime.getStockId();
			TimeShareVO timeShareVO =new TimeShareVO();
			BeanUtils.copyProperties(realTime, timeShareVO);
			timeShareVO.setDate(new Date(System.currentTimeMillis()));
			timeShareVO.setRealTime(new Time(System.currentTimeMillis()));
			
			timeShareList.add(timeShareVO);
			rabbitTemplate.convertAndSend("timeShareExchange","stock.SZSE."+stockID,JSON.toJSONString(timeShareVO));
		}
		
		saveTimeShare(timeShareList);
	
	}

	private void saveTimeShare(List<TimeShareVO> timeShareList) {
		// TODO Auto-generated method stub
		List<TimeShare> timeShare = new ArrayList<>();
		BeanUtils.copyProperties(timeShareList, timeShare);
		
		timeShareRepository.saveAll(timeShare);
	}
	

}
