package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.VO.StockListVO;
import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.service.RealTimeService;

import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

@Service
@Transactional
public class RealTimeServiceImpl implements RealTimeService {

	private Environment env;

	@Autowired
	AmqpAdmin amqpAdmin;

	@Autowired
	RabbitMqConfig rabbitmqConfig;

	@Autowired
	RabbitTemplate rabbitTemplate;

	public static final String ALL_REALTIME_REDIS = "allRealTimeRedis";
	
	@Autowired
	private RealTime2Redis realTime2Redis;
	@Autowired
	private RealTime1Redis realTime1Redis;



	@Override
//	@Scheduled(fixedRate = 6000)
	public void sendRealTime() {
		//updateRealTime();
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		// redis 中读取数据
		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();
		
		List<RealTime2> list2 = new ArrayList<>();
		list2 = realTime2Redis.getAll();
		
	
		realTimeList = finalRealTime(list1,list2);
		// TODO Auto-generated method stub
		for (RealTimeVO realTime : realTimeList) {
			int stockID = realTime.getStockId();
			rabbitTemplate.convertAndSend("realTimeExchange", "stock.SZSE." + stockID, JSON.toJSONString(realTime));
		}

	}
	
	//界面中某只个股点进去 展示个股的实时信息
	@Override
	public RealTimeVO  findRealTime(int stockId) {
		
		List<RealTime1> list1 = new ArrayList<>();
		list1.add(  realTime1Redis.get(String.valueOf(stockId)) );
		
		List<RealTime2> list2 = new ArrayList<>();
		list2.add( realTime2Redis.get(String.valueOf(stockId)) );
		
		return finalRealTime(list1,list2).get(0);
	}
	
	
	
	
    public List<RealTimeVO> finalRealTime(List<RealTime1> realTime1List,List<RealTime2> realTime2List) {
    	Map<Integer, RealTime2> map = realTime2List.stream().collect(Collectors.toMap(RealTime2::getStockId, a -> a,(k1,k2)->k1));
    	List<RealTimeVO> realTimeVOList=new ArrayList<>();
    	for(RealTime1 rt : realTime1List) {
    		
    		//涨跌幅= (最新价-昨日收盘价)/昨日收盘价
    		double increase =(rt.getLastTradePrice()-map.get(rt.getStockId()).getYesterdayClosePrice())/map.get(rt.getStockId()).getYesterdayClosePrice();		
    		//总市值=股价*总股本数
    		double totalMarketCapitalization = rt.getLastTradePrice()*map.get(rt.getStockId()).getTotalShareCapital();
    		//市盈率=股价/每股收益
    		double peRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getEarningsPerShare();
    		//市净率=每股市价/每股净资产
    		double pbRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getBookValue();
    		
    		RealTimeVO realTimeVO=new RealTimeVO();
    		BeanUtils.copyProperties(rt, realTimeVO);
    		
    		realTimeVO.setStockName(map.get(rt.getStockId()).getStockName());
    		realTimeVO.setIncrease(increase);
    		realTimeVO.setYesterdayOpenPrice(map.get(rt.getStockId()).getYesterdayOpenPrice());
    		realTimeVO.setYesterdayClosePrice(map.get(rt.getStockId()).getYesterdayClosePrice());
    		realTimeVO.setTotalMarketCapitalization(totalMarketCapitalization);
    		realTimeVO.setPeRatio(peRatio);
    		realTimeVO.setPbRatio(pbRatio);
    		realTimeVO.setTradeMarket(map.get(rt.getStockId()).getTradeMarket());
    		
    		realTimeVOList.add(realTimeVO);
    	}
    	
    	
    	//logger.info("传进来的用户ownerId："+id);
    	//logger.info("传出去的结果："+list);
    	return realTimeVOList;
    }
}
