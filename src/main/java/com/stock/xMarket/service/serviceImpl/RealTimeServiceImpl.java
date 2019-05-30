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


	@Override
	public void updateRealTime() {
		
		
//		String id=tradeOrder.getStockID();
//		
//		RealTimeVO realTime=realTimeRedis.get(id);
//		
//		realTime.setLatestPrice(tradeOrder.getTradePrice());
//		if(tradeOrder.getTradePrice()<realTime.getLowestPrice()) {
//			realTime.setLowestPrice(tradeOrder.getTradePrice());
//		}else if(tradeOrder.getTradePrice()>realTime.getLowestPrice()) {
//			realTime.setHighestPrice(tradeOrder.getTradePrice());
//		}
//		realTime.setUpsAndDowns(tradeOrder.getTradePrice()-realTime.getOpenPrice());
//		realTime.setIncrease((tradeOrder.getTradePrice()-realTime.getOpenPrice())/realTime.getOpenPrice());
//		
//		
//		realTimeRedis.put(id, domain,-1);
//		
//		String courseListString = (String) realTimeRedis.getString(ALL_REALTIME_REDIS);
//		realTimeList = JSON.parseArray(courseListString, RealTimeVO.class);	
//		for(int i=0; i<realTimeList.size();i++) {
//			RealTimeVO realTime=new RealTimeVO(600000,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
//
//			realTimeList.add(realTime);
//		}
		
		
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		RealTimeVO realTime=new RealTimeVO(600000,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10);
		realTimeList.add(realTime);
		RealTimeVO realTime1=new RealTimeVO(600000,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10);
		realTimeList.add(realTime1);
		String coursesString = JSON.toJSONString(realTimeList);
		realTimeRedis.putString(ALL_REALTIME_REDIS, coursesString, -1);
		
	}
	
	
	
	@Override
//	@Scheduled(fixedRate = 6000)
	public void sendRealTime() {
		updateRealTime();
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
