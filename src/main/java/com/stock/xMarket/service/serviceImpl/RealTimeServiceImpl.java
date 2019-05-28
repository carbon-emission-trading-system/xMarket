package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.service.RealTimeService;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;




@Service
@Transactional
public class RealTimeServiceImpl implements RealTimeService{

	private Environment env;
	
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Autowired
	RabbitMqConfig rabbitmqConfig;

	@Autowired
	RabbitTemplate rabbitTemplate;

	public static final String ALL_REALTIME_REDIS="allRealTimeRedis";
	

	@Autowired
	private RealTimeRedis realTimeRedis;


	@Scheduled(fixedRate = 6000)
	public void updateAndSendRealTime() {
		
		updateRealTime();
		sendRealTime();
		
	}

	public void updateRealTime() {
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		RealTimeVO realTime=new RealTimeVO(600000,10,
				10, 10, 10, 10, 10,
				10, 10, 10, 10, 10, 
				10, 10, 10, 10);

		RealTimeVO realTime2=new RealTimeVO(600446,10,
				10, 10, 10, 10,
				10, 10, 10, 10, 10,
				10, 10, 10, 10, 10);
		realTimeList.add(realTime);
		realTimeList.add(realTime2);
		String coursesString = JSON.toJSONString(realTimeList);
		realTimeRedis.putString(ALL_REALTIME_REDIS, coursesString, -1);
		
	}
	
	
	
	@Override
	public void sendRealTime() {
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		//redis 中读取数据
		String courseListString = (String) realTimeRedis.getString(ALL_REALTIME_REDIS);
		realTimeList = JSON.parseArray(courseListString, RealTimeVO.class);	
		// TODO Auto-generated method stub
		for(RealTimeVO realTime:realTimeList) {
			int stockID=realTime.getStockID();
			rabbitTemplate.convertAndSend("realTimeExchange","stock.SZSE."+stockID,JSON.toJSONString(realTime));
		}
		
	}
}
