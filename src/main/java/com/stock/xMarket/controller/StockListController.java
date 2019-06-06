package com.stock.xMarket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stock.xMarket.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.StockListVO;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.service.StockListService;

@RestController
public class StockListController extends BaseApiController {

	@Autowired
    private StockListService realTimeService;
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);

	//所有个股信息，展示股票列表
    @RequestMapping(value = "/stockList", method = RequestMethod.GET)
    public CommonReturnType findAllRealTime() {
//
//    	List<RealTime2> realTime2List = realTimeService.findRealTime2();
//    	List<RealTime1> realTime1List = realTimeService.findRealTime1();
//    	//DecimalFormat df=new DecimalFormat("0.0000");//设置保留位数
//    	List<StockListVO> realTimeVOList = new ArrayList<StockListVO>();
//
//    	return finalResult(realTime1List,realTime2List,realTimeVOList);
		List<StockListVO> stockListVOList = new ArrayList<>();
		StockListVO stockListVO = new StockListVO();
		stockListVO.setHighestPrice(20);
		stockListVO.setIncrease(1.9);
		stockListVO.setLastTradePrice(19.8);
		stockListVO.setLowestPrice(19);
		stockListVO.setOpenPrice(19.5);
		stockListVO.setPbRatio(5.5);
		stockListVO.setPeRatio(5.5);
		stockListVO.setStockId(100000);
		stockListVO.setStockName("张浩股份");
		stockListVO.setTotalMarketCapitalization(5.5);
		stockListVO.settradeAmount(9.9);
		stockListVO.setTradeMarket(1);
		stockListVO.setYesterdayOpenPrice(19.7);
		stockListVOList.add(stockListVO);
		stockListVO.setStockName("王hs股份");
		stockListVOList.add(stockListVO);
		return CommonReturnType.success(stockListVOList);

    }

    public CommonReturnType finalResult(List<RealTime1> realTime1List,List<RealTime2> realTime2List,List<StockListVO> stockListVOList) {
    	Map<Integer, RealTime2> map = realTime2List.stream().collect(Collectors.toMap(RealTime2::getStockId, a -> a,(k1,k2)->k1));

    	for(RealTime1 rt : realTime1List) {

    		//涨跌幅= (最新价-昨日收盘价)/昨日收盘价
    		double increase =(rt.getLastTradePrice()-map.get(rt.getStockId()).getYesterdayClosePrice())/map.get(rt.getStockId()).getYesterdayClosePrice();
    		//总市值=股价*总股本数
    		double totalMarketCapitalization = rt.getLastTradePrice()*map.get(rt.getStockId()).getTotalShareCapital();
    		//市盈率=股价/每股收益
    		double peRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getEarningsPerShare();
    		//市净率=每股市价/每股净资产
    		double pbRatio = rt.getLastTradePrice()/map.get(rt.getStockId()).getBookValue();

    		StockListVO stockListVO=new StockListVO();
    		BeanUtils.copyProperties(rt, stockListVO);

    		stockListVO.setStockName(map.get(rt.getStockId()).getStockName());
    		stockListVO.setIncrease(increase);
    		stockListVO.setYesterdayOpenPrice(map.get(rt.getStockId()).getYesterdayOpenPrice());
    		stockListVO.setTotalMarketCapitalization(totalMarketCapitalization);
    		stockListVO.setPeRatio(peRatio);
    		stockListVO.setPbRatio(pbRatio);
    		stockListVO.setTradeMarket(map.get(rt.getStockId()).getTradeMarket());

    		stockListVOList.add(stockListVO);
    	}


    	//logger.info("传进来的用户ownerId："+id);
    	//logger.info("传出去的结果："+list);
    	return CommonReturnType.success(stockListVOList);
    }

    //根据用户id，展示用户的所有自选股信息
    @RequestMapping(value = "/api/selfSelectStockList/{id}", method = RequestMethod.GET)
    public CommonReturnType findAllSelfSelectStock(@PathVariable("id") int id) {

    	List<RealTime1> realTime1List = realTimeService.findSelfSelectStockRealTime1(id);
    	List<RealTime2> realTime2List = realTimeService.findSelfSelectStockRealTime2(id);
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

    	return finalResult(realTime1List,realTime2List,stockListVOList);
    }


}
