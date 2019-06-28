package com.xMarket.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xMarket.VO.TransactionOrderVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.TransactionOrder;
import com.xMarket.response.CommonReturnType;
import com.xMarket.service.TransactionOrderService;

@RestController
public class TransactionOrderController extends BaseApiController {
	@Autowired
    private TransactionOrderService transactionOrderService;
	final static Logger logger=LoggerFactory.getLogger(TransactionOrderController.class);
	 
	//找到用户的所有历史成交单信息
    @RequestMapping(value = "/historyExchangeInfo", method = RequestMethod.GET)
    public CommonReturnType findAllHisExcInfo(@RequestParam("userId") int userId) {
    	
    	logger.info("传进来的用户ownerId："+userId);
    	List<TransactionOrderVO> list=transactionOrderService.findByOwnerId(userId);
    	logger.info("传出去的结果："+list);
    	return CommonReturnType.success(list);
    }
    
	//找到用户的所有当日成交单信息
    @RequestMapping(value = "/todayExchange", method = RequestMethod.GET)
    public CommonReturnType todayExchange(@RequestParam("userId") int userId) throws BusinessException {
    	
    	logger.info("传进来的用户ownerId："+userId);
    	List<TransactionOrder> list=transactionOrderService.findByOwnerIdAndDate(userId);
    	//List<TransactionOrderVO> VOlist=transactionOrderService.findByOwnerId(userId);
    	logger.info("传出去的结果："+JSON.toJSONString(list));
    	return CommonReturnType.success(list);
    }

}
