package com.stock.xMarket.controller;

import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.VO.UserVO;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTimeRedis;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeController extends BaseApiController {

    @Autowired
    private RealTime1Redis realTime1Redis;

    @Autowired
    private UserFundRepository userFundRepository;

    @Autowired
    private StockRepository stockRepository;//后续可去除

    final static Logger logger=LoggerFactory.getLogger(TradeController.class);

    @RequestMapping(value = "/api/QueryStockInformation", method = RequestMethod.GET)
    public CommonReturnType QueryStockInformation(@RequestParam(name = "stockId")int stockId, @RequestParam(name = "userId")int userId){
        logger.info("后端接收到查询请求"+"查询股票"+stockId+"查询用户"+userId);
        return CommonReturnType.success(createStockTradeVO(stockId,userId));
    }



    //创建StockTradeVO的方法
    public StockTradeVO createStockTradeVO(int stockId,int userId){
        StockTradeVO stockTradeVO = new StockTradeVO();
        RealTime1 realTime1 = realTime1Redis.get(String.valueOf(stockId));
        UserFund userFund = userFundRepository.findByUser_UserId(userId);
        Stock stock = stockRepository.findById(stockId).get();
        stockTradeVO.setUserMoney(userFund.getBalance());
        stockTradeVO.setStockId(stockId);
        stockTradeVO.setOrderPrice(realTime1.getLastTradePrice());
        stockTradeVO.setTradeMarket(stock.getTradeMarket());
        stockTradeVO.setStockName(stock.getStockName());
        stockTradeVO.setOpenPrice(realTime1.getOpenPrice());
        return stockTradeVO;
    }


}
