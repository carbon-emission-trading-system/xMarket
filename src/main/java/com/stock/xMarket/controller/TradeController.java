package com.stock.xMarket.controller;

import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.VO.UserVO;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserFundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeController {

    @Autowired
    private RealTimeRedis realTimeRedis;

    @Autowired
    private UserFundRepository userFundRepository;

    @Autowired
    private StockRepository stockRepository;//后续可去除

    final static Logger logger=LoggerFactory.getLogger(TransactionOrderController.class);


    //创建StockTradeVO的方法
    public StockTradeVO createStockTradeVO(int stockId,int userId){
        StockTradeVO stockTradeVO = new StockTradeVO();
        UserFund userFund = userFundRepository.findByUser_UserId(userId);
        Stock stock = stockRepository.findById(stockId);
        stockTradeVO.setUserMoney(userFund.getBalance());
        stockTradeVO.setStockId(stockId);
        stockTradeVO.setOrderPrice(1.78);
        stockTradeVO.setTradeMarket(stock.getTradeMarket());
        stockTradeVO.setStockName(stock.getStockName());
    }


}
