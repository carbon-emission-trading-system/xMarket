package com.stock.xMarket.controller;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.KLineDataVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.StockHistory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StockInformationControllerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void createDayKLineDataVOList() throws BusinessException {
        StockInformationController c = new StockInformationController();
        List<StockHistory> list = new ArrayList<>();
        StockHistory  kLineDataVO = new StockHistory();
        kLineDataVO.setClosePrice(1.2);
        kLineDataVO.setHighestPrice(1.5);
        kLineDataVO.setLowestPrice(1.1);
        kLineDataVO.setOpenPrice(1.4);
        kLineDataVO.setVolume(12000);
        list.add(kLineDataVO);
        System.out.println(JSON.toJSONString(c.createDayKLineDataVOList(list)));
    }
}