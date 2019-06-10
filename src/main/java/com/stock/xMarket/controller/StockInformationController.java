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
public class StockInformationController extends BaseApiController{

    final static Logger logger=LoggerFactory.getLogger(StockInformationController.class);

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @RequestMapping(value = "/KlineDiagramDisplay", method = RequestMethod.GET)
    public CommonReturnType KlineDiagramDisplay(@RequestParam(name = "stockId")int stockId) throws BusinessException, ParseException {
        logger.info("后端接收到建立K线图请求"+" 股票："+stockId);
        return CommonReturnType.success(createDayKLineDataVOList(stockHistoryRepository.findAllByStockId(stockId)));
//        List<KLineDataVO> kLineDataVOList = new ArrayList<>();
//        KLineDataVO kLineDataVO1 = new KLineDataVO("2019-08-01",40,41,42,39.8,90000);
//        kLineDataVOList.add(kLineDataVO1);
//        KLineDataVO kLineDataVO2 = new KLineDataVO("2019-08-02",41,43,43.2,40.9,90000);
//        kLineDataVOList.add(kLineDataVO2);
//        KLineDataVO kLineDataVO3 = new KLineDataVO("2019-08-03",38,36,38.2,35.9,90000);
//        kLineDataVOList.add(kLineDataVO3);
//        KLineDataVO kLineDataVO4 = new KLineDataVO("2019-08-04",41,43,43.2,40.9,90000);
//        kLineDataVOList.add(kLineDataVO4);
//        KLineDataVO kLineDataVO5 = new KLineDataVO("2019-08-05",50,49,54,47,90000);
//        kLineDataVOList.add(kLineDataVO5);
//        KLineDataVO kLineDataVO6 = new KLineDataVO("2019-08-08",48,51,52.1,46,90000);
//        kLineDataVOList.add(kLineDataVO6);
//        return CommonReturnType.success(kLineDataVOList);
    }

    //用于复制stockHistory列表信息至KLineDataVO列表的函数
    //使用了JSON工具的复制方法
    public List<KLineDataVO> createDayKLineDataVOList(List<StockHistory> stockHistoriesList) throws BusinessException {
        if (CollectionUtils.isEmpty(stockHistoriesList)) {
            logger.error("K线图数据获取失败，没有查询到此股票的历史信息");
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"K线图数据获取失败，没有查询到此股票的历史信息");
        }
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        return JSON.parseArray(JSON.toJSONString(stockHistoriesList,SerializerFeature.WriteDateUseDateFormat), KLineDataVO.class);
    }



}
