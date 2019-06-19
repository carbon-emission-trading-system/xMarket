package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.IndexVO;
import com.stock.xMarket.VO.IndexVO2;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.model.Index;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.redis.IndexRedis;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.service.IndexService;
import com.stock.xMarket.service.RealTimeService;

@Service
@Transactional
public class IndexServiceImpl implements IndexService{

	@Autowired
	private RealTime2Redis realTime2Redis;
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private IndexRedis indexRedis;
	
	@Autowired
	private RealTime1Redis realTime1Redis;
	
	@Autowired
	private RealTimeService realTimeService;
	
	
	
	
	
	
	
	
	@Scheduled(fixedRate = 10000)
	public void sendIndex() {
		//updateRealTime();
		List<RealTimeVO> realTimeList = new ArrayList<RealTimeVO>();
		// redis 中读取数据
		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();
		List<RealTime2> list2 = new ArrayList<>();
		list2 = realTime2Redis.getAll();
		realTimeList =realTimeService. finalRealTime(list1,list2);
		// TODO Auto-generated method stub
		
		List<IndexVO> indexVOList=indexRedis.getAll();
		
		
		
		for(IndexVO indexVO:indexVOList) {
		
		String key=String.valueOf(indexVO.getIndexId());
			
		int totalVolume=0;
		double totalTradeAmount=0;
		double totalMarketCapitalization=0;
		int increases=0;
		int decrease=0;
		int flats=0;
		for (RealTimeVO realTime : realTimeList) {
			if(realTime.getTradeMarket()==indexVO.getTradeMarket()) {

				
				totalMarketCapitalization+=realTime.getTotalMarketCapitalization();
				totalVolume+=realTime.getVolume();
				totalTradeAmount+=realTime.getTradeAmount();
				
				if(realTime.getIncrease()>0)
					increases++;
				else if(realTime.getIncrease()<0)
					decrease++;
				else
					flats++;
					
					
				
			}
		}
			double nowIndex=totalMarketCapitalization/indexVO.getBaseCapitalization()*indexVO.getBaseIndex();
			
			
			indexVO.setTradeAmount(totalTradeAmount);
			
			indexVO.setLastIndex(nowIndex);
			
			indexVO.setVolume(totalVolume);
			
			
			
			if(nowIndex>indexVO.getHighestIndex())
				indexVO.setHighestIndex(nowIndex);
			if(nowIndex<indexVO.getLowestIndex())
				indexVO.setLowestIndex(nowIndex);
			
			indexVO.setIncrease(indexVO.getLastIndex()-indexVO.getYesterdayCloseIndex());
			indexVO.setUpsAndDowns((indexVO.getLastIndex()-indexVO.getYesterdayCloseIndex())/indexVO.getYesterdayCloseIndex());
			
			indexVO.setIncreaseStocks(increases); 

			indexVO.setDecreaseStocks(decrease);
			
			indexVO.setFlatStocks(flats);
			
			indexVO.setMarketCapitalization(totalMarketCapitalization);
			
			indexRedis.put(key, indexVO, -1);
			
			rabbitTemplate.convertAndSend("realTimeExchange", "index." + indexVO.getIndexId(), JSON.toJSONString(indexVO));
			
			
		}
		
		
		
	}


	@Override
	public ArrayList<IndexVO2> getIndex() {
		// TODO Auto-generated method stub
		List<IndexVO> indexVOList=indexRedis.getAll();
		ArrayList<IndexVO2> indexVO2List=new ArrayList<>();
		
		
		for(IndexVO indexVO:indexVOList) {
			IndexVO2 indexVO2=new IndexVO2();
			BeanUtils.copyProperties(indexVO, indexVO2);
			indexVO2List.add(indexVO2);
			
		}
		
		return indexVO2List;
	}


	@Override
	public IndexVO indexInfo(int indexId) {
		// TODO Auto-generated method stub
		
		IndexVO indexVO=indexRedis.get(String.valueOf(indexId));
		return indexVO;
	}
		
	}