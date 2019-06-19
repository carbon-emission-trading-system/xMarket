package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.StockListVO;
import com.stock.xMarket.controller.KlineController;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.model.SelfSelectStock;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.repository.SelfSelectStockRepository;
import com.stock.xMarket.service.StockListService;
import com.stock.xMarket.util.DemicalUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StockListServiceImpl implements StockListService {
	final static Logger logger = LoggerFactory.getLogger(KlineController.class);

	@Autowired
	private RealTime2Redis realTime2Redis;
	@Autowired
	private RealTime1Redis realTime1Redis;
	@Autowired
	private SelfSelectStockRepository selfSelectStockRepository;

	// 展示个股股票列表
	@Override
	public List<RealTime2> findRealTime2() {

		List<RealTime2> list = new ArrayList<>();
		list = realTime2Redis.getAll();
		return list;
	}

	// 展示个股股票列表
	@Override
	public List<RealTime1> findRealTime1() {

		List<RealTime1> list1 = new ArrayList<>();
		list1 = realTime1Redis.getAll();
		return list1;
	}

	// 根据用户id，展示自选股列表
	@Override
	public List<RealTime1> findSelfSelectStockRealTime1(int userId) throws BusinessException {

		List<SelfSelectStock> selfSelectStockList = new ArrayList<>();
		selfSelectStockList = selfSelectStockRepository.findByUserId(userId);

		List<RealTime1> realTime1List = new ArrayList<>();
		for (SelfSelectStock s : selfSelectStockList) {

			String key = s.getStockId();
			if (realTime1Redis.hasKey(key)) {
				RealTime1 realTime1 = realTime1Redis.get(key);
				realTime1List.add(realTime1);

			}
		}

		return realTime1List;
	}

	@Override
	public List<RealTime2> findSelfSelectStockRealTime2(int userId) throws BusinessException {
		List<SelfSelectStock> selfSelectStockList = new ArrayList<>();
		selfSelectStockList = selfSelectStockRepository.findByUserId(userId);

		List<RealTime2> realTime2List = new ArrayList<>();

		for (SelfSelectStock s : selfSelectStockList) {
			String key = String.valueOf(s.getStockId());
			if (realTime2Redis.hasKey(key)) {
				RealTime2 realTime2 = realTime2Redis.get(key);
				realTime2List.add(realTime2);

			}
		}

		return realTime2List;
	}
	
	@Override
    public List<StockListVO> finalList(List<RealTime1> realTime1List,List<RealTime2> realTime2List,List<StockListVO> stockListVOList) {
    	Map<String, RealTime2> map = realTime2List.stream().collect(Collectors.toMap(RealTime2::getStockId, a -> a,(k1,k2)->k1));

    	for(RealTime1 rt : realTime1List) {

    		//涨跌幅= (最新价-昨日收盘价)/昨日收盘价
    		double increase =(rt.getLastTradePrice()-rt.getYesterdayClosePrice())/rt.getYesterdayClosePrice();
    		//总市值=股价*总股本数
    		double totalMarketCapitalization = rt.getLastTradePrice()*map.get(rt.getStockId()).getTotalShareCapital();
    		//市盈率=股价/每股收益
    		double peRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getEarningsPerShare();
    		//市净率=每股市价/每股净资产
    		double pbRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getBookValue();

    		StockListVO stockListVO=new StockListVO();
    		BeanUtils.copyProperties(rt, stockListVO);

    		stockListVO.setStockName(map.get(rt.getStockId()).getStockName());
    		stockListVO.setStockPinyin(map.get(rt.getStockId()).getStockPinyin());
    		stockListVO.setIncrease(DemicalUtil.keepTwoDecimal(increase*100));
    		stockListVO.setYesterdayOpenPrice(DemicalUtil.keepTwoDecimal(map.get(rt.getStockId()).getYesterdayOpenPrice()));
    		stockListVO.setTotalMarketCapitalization(DemicalUtil.keepTwoDecimal(totalMarketCapitalization/100000000));//以亿为单位
    		stockListVO.setPeRatio(DemicalUtil.keepTwoDecimal(peRatio));
    		stockListVO.setPbRatio(DemicalUtil.keepTwoDecimal(pbRatio));
    		stockListVO.setTradeMarket(map.get(rt.getStockId()).getTradeMarket());

    		stockListVOList.add(stockListVO);
    	}


    	//logger.info("传进来的用户ownerId："+id);
    	//logger.info("传出去的结果："+list);
    	return stockListVOList;
    }
	
	
	
	

}
