package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.repository.StockHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stock.xMarket.VO.KLineDataVO;
import com.stock.xMarket.controller.KlineController;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.StockHistory;
import com.stock.xMarket.service.KlineService;

@Service
@Transactional
public class KlineServiceImpl implements KlineService{


    final static Logger logger=LoggerFactory.getLogger(KlineController.class);

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

	 //用于复制stockHistory列表信息至KLineDataVO列表的函数
    //使用了JSON工具的复制方法
    @Override
    public List<KLineDataVO> createDayKLineDataVOList(String stockId) throws BusinessException {
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAllByStockIdOrderByDate(stockId);
        if (CollectionUtils.isEmpty(stockHistoryList)) {
            logger.error("K线图数据获取失败，没有查询到历史信息");
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"K线图数据获取失败，没有查询到历史信息");
        }
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        return JSON.parseArray(JSON.toJSONString(stockHistoryList,SerializerFeature.WriteDateUseDateFormat), KLineDataVO.class);
    }
	
}
