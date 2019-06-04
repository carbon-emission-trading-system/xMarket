package com.stock.xMarket.controller;

import java.util.List;

import com.stock.xMarket.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.TransactionOrderVO;
import com.stock.xMarket.service.TransactionOrderService;

@RestController
public class TransactionOrderController extends BaseApiController {
	@Autowired
    private TransactionOrderService transactionOrderService;
	final static Logger logger=LoggerFactory.getLogger(TransactionOrderController.class);
	 
	//找到用户的所有历史成交单信息
    @RequestMapping(value = "/api/historyExchangeInfo/{id}", method = RequestMethod.GET)
    public CommonReturnType findAllHisExcInfo(@PathVariable("id") int id) {
    	
    	logger.info("传进来的用户ownerId："+id);
    	List<TransactionOrderVO> list=transactionOrderService.findByOwnerId(id);
    	logger.info("传出去的结果："+list);
    	return CommonReturnType.success(list);
    }

}
