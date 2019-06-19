package com.stock.xMarket.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.ConditionVO;
import com.stock.xMarket.VO.StockListVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;
import com.stock.xMarket.model.User;
import com.stock.xMarket.service.RankingListService;
import com.stock.xMarket.service.StockListService;
import com.stock.xMarket.util.DemicalUtil;

@RestController
public class StockListController extends BaseApiController {

	@Autowired
    private StockListService stockListService;
	
	@Autowired
    private RankingListService rankingListService;
	
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);

	//所有个股信息，展示股票列表
    @RequestMapping(value = "/stockList", method = RequestMethod.GET)
    public CommonReturnType findAllRealTime() {
    	
    	List<RealTime2> realTime2List = stockListService.findRealTime2();
    	List<RealTime1> realTime1List = stockListService.findRealTime1();
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();
    	
    	stockListVOList=stockListService.finalList(realTime1List, realTime2List, stockListVOList);
    	
    	return CommonReturnType.success(stockListVOList);
    			
    	


    }
    
  
   
	//所有个股信息，展示股票列表
    @RequestMapping(value = "/conditionalStockList", method = RequestMethod.POST)
    public CommonReturnType conditionalStockList(@ModelAttribute(value = "condition")ConditionVO conditionVO) {
    	
    	List<RealTime2> realTime2List = stockListService.findRealTime2();
    	List<RealTime1> realTime1List = stockListService.findRealTime1();
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();
    	
    	stockListVOList=stockListService.finalList(realTime1List, realTime2List, stockListVOList);
    	
    	for(StockListVO stockListVO:stockListVOList) {
    		
    		if(stockListVO.getIncrease()<conditionVO.getMinIncrease()||stockListVO.getIncrease()>conditionVO.getMaxIncrease()) {
    			stockListVOList.remove(stockListVO);
    		}
    		
    		if(stockListVO.getTotalMarketCapitalization()<conditionVO.getMinTotalMarketCapitalization() ||stockListVO.getTotalMarketCapitalization()>conditionVO.getMaxTotalMarketCapitalization()) {
    			stockListVOList.remove(stockListVO);
    		}
    		
    		if(stockListVO.getPeRatio()<conditionVO.getMinPeRatio()||stockListVO.getPeRatio()>conditionVO.getMaxPeRatio()) {
    			stockListVOList.remove(stockListVO);
    		}
    		
    		if(stockListVO.getTradeAmount()<conditionVO.getMinTradeAmount()||stockListVO.getTradeAmount()>conditionVO.getMaxTradeAmount()) {
    			stockListVOList.remove(stockListVO);
    		}
    		
    	}
    	
    	return CommonReturnType.success(stockListVOList);
    			
    	


    }
    
  //所有个股信息，展示股票列表
    @RequestMapping(value = "/getCondition", method = RequestMethod.GET)
    public CommonReturnType getCondition() {
    	
    	List<RealTime2> realTime2List = stockListService.findRealTime2();
    	List<RealTime1> realTime1List = stockListService.findRealTime1();
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();
    	
    	ConditionVO conditionVO=new ConditionVO();
    	
    	stockListVOList=stockListService.finalList(realTime1List, realTime2List, stockListVOList);
    	
    	for(StockListVO stockListVO:stockListVOList) {
    		
    		if(stockListVO.getIncrease()<conditionVO.getMinIncrease()) {
    			conditionVO.setMinIncrease(stockListVO.getIncrease());
    		}
    		
    		if(stockListVO.getIncrease()>conditionVO.getMaxIncrease()) {
    			conditionVO.setMaxIncrease(stockListVO.getIncrease());
    		}
    		
    		
    		if(stockListVO.getTotalMarketCapitalization()<conditionVO.getMinTotalMarketCapitalization()) {
    			conditionVO.setMinTotalMarketCapitalization(stockListVO.getTotalMarketCapitalization());
    		}
    		
    		if(stockListVO.getTotalMarketCapitalization()>conditionVO.getMaxTotalMarketCapitalization()) {
    			conditionVO.setMaxTotalMarketCapitalization(stockListVO.getTotalMarketCapitalization());
    		}
    		
    		
    		if(stockListVO.getPeRatio()<conditionVO.getMinPeRatio()) {
    			conditionVO.setMinPeRatio(stockListVO.getPeRatio());
    		}
    		
    		if(stockListVO.getPeRatio()>conditionVO.getMaxPeRatio()) {
    			conditionVO.setMaxPeRatio(stockListVO.getPeRatio());
    		}
    		

    		if(stockListVO.getTradeAmount()<conditionVO.getMinTradeAmount()) {
    			conditionVO.setMinTradeAmount(stockListVO.getTradeAmount());
    		}
    		

    		if(stockListVO.getTradeAmount()>conditionVO.getMaxTradeAmount()) {
    			conditionVO.setMaxTradeAmount(stockListVO.getTradeAmount());
    		}
    		
    	}
    	
    	return CommonReturnType.success(conditionVO);
    			

    }
    
    
    

    //根据用户id，展示用户的所有自选股信息
    @RequestMapping(value = "/selfSelectStockList", method = RequestMethod.GET)
    public CommonReturnType findAllSelfSelectStock(@RequestParam("userId") int id) throws BusinessException {
  	
    	List<RealTime1> realTime1List = stockListService.findSelfSelectStockRealTime1(id);
    	if(realTime1List == null){
    		throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"realTime1List为空");
		}
    	List<RealTime2> realTime2List = stockListService.findSelfSelectStockRealTime2(id);
		if(realTime2List == null){
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"realTime2List为空");
		}
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

    	stockListVOList=stockListService.finalList(realTime1List, realTime2List, stockListVOList);
    	
    	return CommonReturnType.success(stockListVOList);

    }


    
    @RequestMapping(value = "/rankList", method = RequestMethod.GET)
    public CommonReturnType rankList(@RequestParam("type") int type) throws BusinessException{
    	
   
    	List<RealTime2> realTime2List = stockListService.findRealTime2();
    	List<RealTime1> realTime1List = stockListService.findRealTime1();
    	List<StockListVO> stockListVOList = new ArrayList<StockListVO>();
    	stockListVOList=stockListService.finalList(realTime1List, realTime2List, stockListVOList);
    	
    	
		
		
		switch(type)
	      {
	         case 1 :
	        	rankingListService.rankingByIncrease(stockListVOList); 
	            break;
	         case 2 :
	        	 rankingListService.rankingByDecrease(stockListVOList);
	            break;
	         case 3 :
	        	 rankingListService.rankingByExchangeAmount(stockListVOList);
	            break;
	         case 4 :
	        	 rankingListService.rankingByconversionHand(stockListVOList);
	            break;
	         default :
	          throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
	      }
		

    	return CommonReturnType.success(stockListVOList);
    }
    
    
    
}
