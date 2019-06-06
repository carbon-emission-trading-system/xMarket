package com.stock.xMarket.controller;

import java.util.List;

import com.stock.xMarket.VO.HistoryHoldPositionVO;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.HistoryHoldPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import static com.stock.xMarket.response.CommonReturnType.*;

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
