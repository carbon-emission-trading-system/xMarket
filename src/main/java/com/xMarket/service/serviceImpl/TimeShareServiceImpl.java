package com.xMarket.service.serviceImpl;

import java.math.BigDecimal;
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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xMarket.VO.IndexVO;
import com.xMarket.VO.KLineDataVO;
import com.xMarket.VO.RealTimeVO;
import com.xMarket.VO.TimeShareVO;
import com.xMarket.config.RabbitMqConfig;
import com.xMarket.model.Index;
import com.xMarket.model.RealTime1;
import com.xMarket.model.RealTime2;
import com.xMarket.model.Stock;
import com.xMarket.model.TimeShare;
import com.xMarket.redis.IndexRedis;
import com.xMarket.redis.RealTime1Redis;
import com.xMarket.redis.RealTime2Redis;
import com.xMarket.redis.TimeShareRedis;
import com.xMarket.repository.IndexRepository;
import com.xMarket.repository.StockRepository;
import com.xMarket.repository.TimeShareRepository;
import com.xMarket.repository.UserRepository;
import com.xMarket.service.RealTimeService;
import com.xMarket.service.TimeShareService;

@Service
//@Transactional
public class TimeShareServiceImpl implements TimeShareService {
	
	@Autowired
	private RealTime2Redis realTime2Redis;

	@Autowired
	private RealTime1Redis realTime1Redis;
	
	@Autowired
	private TimeShareRedis timeShareRedis;
	
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
	
	@Autowired
	public StockRepository stockRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Autowired
	private IndexRedis indexRedis;
	
	  /*	 * 获得的是double类型	 * 保留两位小数        */	
    public double keepDecimal(double num){		
    	if(num==Double.POSITIVE_INFINITY||num==Double.NEGATIVE_INFINITY||Double.isNaN(num))
    		return num;
    	BigDecimal bg = new BigDecimal(num);		
    	double num1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		
    	return num1;
    }
	
	@Override
//    @Scheduled(cron = "0 30-59 9 ? * MON-FRI")
//	@Scheduled(cron = "0 0-59 10 ? * MON-FRI")
//	@Scheduled(cron = "0 0-30 11 ? * MON-FRI")
//	@Scheduled(cron = "0 0-30 13 ? * MON-FRI")
//	@Scheduled(cron = "0 0-59 14-15 ? * MON-FRI")
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
			String stockID=realTime.getStockId();
			TimeShareVO timeShareVO =new TimeShareVO();
			BeanUtils.copyProperties(realTime, timeShareVO);
			timeShareVO.setDate(new Date(System.currentTimeMillis()));
			timeShareVO.setRealTime(new Time(System.currentTimeMillis()));
			if(realTime.getVolume()!=0&&realTime.getTradeAmount()!=0) {
				timeShareVO.setAveragePrice(keepDecimal(realTime.getTradeAmount()/realTime.getVolume()));
				timeShareVO.setVolume(realTime.getVolume()-timeShareRedis.get(String.valueOf(stockID)));
			}
			timeShareRedis.put(String.valueOf(stockID), realTime.getVolume(), -1);
			
			timeShareList.add(timeShareVO);
			
			 JSON.DEFFAULT_DATE_FORMAT = "HH:mm";
			
			rabbitTemplate.convertAndSend("timeShareExchange","stock.SZSE."+stockID,JSON.toJSONString(timeShareVO,SerializerFeature.WriteDateUseDateFormat));
		}
		
		
		
		
		
		List<IndexVO> indexVOList=indexRedis.getAll();
		
		for (IndexVO index : indexVOList) {
			String key = String.valueOf(index.getIndexId());
			
			TimeShareVO timeShareVO =new TimeShareVO();
			timeShareVO.setStockId(index.getIndexId());
			timeShareVO.setDate(new Date(System.currentTimeMillis()));
			timeShareVO.setRealTime(new Time(System.currentTimeMillis()));
			
			if(index.getVolume()!=0&&index.getTradeAmount()!=0) {
				timeShareVO.setAveragePrice(keepDecimal(index.getTradeAmount()/index.getVolume()));
				timeShareVO.setVolume(index.getVolume()-timeShareRedis.get(key));
			}
			timeShareRedis.put(key, index.getVolume(), -1);
			
			timeShareList.add(timeShareVO);
			
			 JSON.DEFFAULT_DATE_FORMAT = "HH:mm";
			
			rabbitTemplate.convertAndSend("timeShareExchange","index."+key,JSON.toJSONString(timeShareVO,SerializerFeature.WriteDateUseDateFormat));
		}
		
		
		
		
		saveTimeShare(timeShareList);
	
	}

	private void saveTimeShare(List<TimeShareVO> timeShareVOList) {
		// TODO Auto-generated method stub
		List<TimeShare> timeShareList = new ArrayList<>();
		
		for(TimeShareVO timeShareVO: timeShareVOList) {
			
			TimeShare timeShare=new TimeShare();
			
			BeanUtils.copyProperties(timeShareVO, timeShare);
			
			timeShareList.add(timeShare);
			
		}
		
		
		
		timeShareRepository.saveAll(timeShareList);
	}
	

}
