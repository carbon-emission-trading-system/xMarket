package com.xMarket.controller;

import static com.xMarket.response.CommonReturnType.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xMarket.VO.HistoryHoldPositionVO;
import com.xMarket.response.CommonReturnType;
import com.xMarket.service.HistoryHoldPositionService;

@RestController
public class HistoryHoldPositionController extends BaseApiController {

	@Autowired
    private HistoryHoldPositionService historyHoldPositionService;

	final static Logger logger=LoggerFactory.getLogger(HistoryHoldPositionController.class);
	 
	//找到用户的所有历史持仓信息
    @RequestMapping(value = "/historyHoldPositionInfo", method = RequestMethod.GET)
    public CommonReturnType findAllHisHolPosInfo(@RequestParam("userId") int userId) {
    	
    	logger.info("传进来的用户id："+userId);
    	List<HistoryHoldPositionVO> list=historyHoldPositionService.findByUserId(userId);
    	logger.info("传出去的结果："+list);
    	return success(list);
   
    }

}
