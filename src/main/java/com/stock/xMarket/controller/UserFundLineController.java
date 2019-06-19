package com.stock.xMarket.controller;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.UserFundLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class UserFundLineController extends BaseApiController {

    final static Logger logger=LoggerFactory.getLogger(KlineController.class);

    @Autowired
    UserFundLineService userFundLineService;

    @RequestMapping(value = "/userFundLineDisplay", method = RequestMethod.GET)
    public CommonReturnType KlineDiagramDisplay(@RequestParam(name = "userId")int userId) throws BusinessException, ParseException {
        logger.info("后端接收到建立资金曲线请求"+" 用户："+userId);
        return CommonReturnType.success(userFundLineService.createUserFundLine(userId));

    }
}
