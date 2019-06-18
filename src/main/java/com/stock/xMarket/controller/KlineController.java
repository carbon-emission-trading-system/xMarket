package com.stock.xMarket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stock.xMarket.VO.KLineDataVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.StockHistory;
import com.stock.xMarket.repository.StockHistoryRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.response.CommonReturnType;
import com.stock.xMarket.service.KlineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class KlineController extends BaseApiController{

    final static Logger logger=LoggerFactory.getLogger(KlineController.class);

    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    
    @Autowired
    KlineService klineService;

    @RequestMapping(value = "/KlineDiagramDisplay", method = RequestMethod.GET)
    public CommonReturnType KlineDiagramDisplay(@RequestParam(name = "stockId")int stockId) throws BusinessException, ParseException {
        logger.info("后端接收到建立K线图请求"+" 股票："+stockId);
        return CommonReturnType.success(klineService.createDayKLineDataVOList(stockHistoryRepository.findAllByStockIdOrderByDate(stockId)));

    }

   



}
