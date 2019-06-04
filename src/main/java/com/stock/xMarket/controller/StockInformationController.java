package com.stock.xMarket.controller;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.KLineDataVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.StockHistory;
import com.stock.xMarket.repository.StockHistoryRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class StockInformationController extends BaseApiController{

    final static Logger logger=LoggerFactory.getLogger(StockInformationController.class);

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @RequestMapping(value = "/api/KlineDiagramDisplay", method = RequestMethod.GET)
    public CommonReturnType KlineDiagramDisplay(@RequestParam(name = "stockId")int stockId, @RequestParam(name = "lineIdentifier")int lineIdentifier) throws BusinessException {
        logger.info("后端接收到建立K线图请求"+" 股票："+stockId);
        return CommonReturnType.success(createDayKLineDataVOList(stockHistoryRepository.findAllByStockId(stockId)));
    }

    //用于复制stockHistory列表信息至KLineDataVO列表的函数
    //使用了JSON工具的复制方法
    public List<KLineDataVO> createDayKLineDataVOList(List<StockHistory> stockHistoriesList) throws BusinessException {
        if (CollectionUtils.isEmpty(stockHistoriesList)) {
            logger.error("K线图数据获取失败，没有查询到此股票的历史信息");
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"K线图数据获取失败，没有查询到此股票的历史信息");
        }
        return JSON.parseArray(JSON.toJSONString(stockHistoriesList), KLineDataVO.class);
    }



}
