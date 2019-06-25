package com.stock.xMarket.controller.system;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.service.OrderService;
import com.stock.xMarket.service.StockListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.util.Random;

@Component
@EnableScheduling
public class SystemTrade {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockListService stockListService;

    @Autowired
    private RealTime1Redis realTime1Redis;

    //指定时间间隔
    @Scheduled(fixedRate=60000)
    private void tradeTasks() throws BusinessException {
        OrderVO orderVO = new OrderVO();
        orderVO.setTradeStraregy(1);
        orderVO.setUserId(1000);
        orderVO.setTime(new Time(System.currentTimeMillis()));
        orderVO.setDate(new Date(System.currentTimeMillis()));
        for (Stock stock : stockListService.findStcokList()){
            orderVO.setStockId(stock.getStockId());
            orderVO.setStockName(stock.getStockName());
            double lastTradePrice = realTime1Redis.get(orderVO.getStockId().toString()).getLastTradePrice();
            double yesterdayClosePrice = realTime1Redis.get(orderVO.getStockId().toString()).getYesterdayClosePrice();
            if (lastTradePrice == 0||lastTradePrice<=yesterdayClosePrice*0.9||lastTradePrice>=yesterdayClosePrice*1.1){
                continue;
            }
            Random rand = new Random();
            systemBuyOrder(orderVO,lastTradePrice);
            systemSellOrder(orderVO,lastTradePrice);
        }
        return;
    }

    private void systemBuyOrder(OrderVO orderVO,double lastTradePrice) throws BusinessException {
        orderVO.setType(0);
        //根据正态分布生成委托单
        orderVO.setOrderPrice(lastTradePrice-0.01);
        orderVO.setOrderAmount(7700);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice-0.02);
        orderVO.setOrderAmount(6600);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice-0.03);
        orderVO.setOrderAmount(4800);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice-0.04);
        orderVO.setOrderAmount(3000);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice-0.05);
        orderVO.setOrderAmount(1600);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice-0.06);
        orderVO.setOrderAmount(700);
        orderService.buyOrSale(orderVO);
    }

    private void systemSellOrder(OrderVO orderVO,double lastTradePrice) throws BusinessException {
        orderVO.setType(1);
        //根据正态分布生成委托单
        orderVO.setOrderPrice(lastTradePrice+0.01);
        orderVO.setOrderAmount(7700);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice+0.02);
        orderVO.setOrderAmount(6600);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice+0.03);
        orderVO.setOrderAmount(4800);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice+0.04);
        orderVO.setOrderAmount(3000);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice+0.05);
        orderVO.setOrderAmount(1600);
        orderService.buyOrSale(orderVO);
        orderVO.setOrderPrice(lastTradePrice+0.06);
        orderVO.setOrderAmount(700);
        orderService.buyOrSale(orderVO);
    }
}
