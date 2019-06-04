package com.stock.xMarket.controller;

import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTime1Redis;
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
    public CommonReturnType QueryStockInformation(@RequestParam(name = "stockId")int stockId, @RequestParam(name = "userId")int userId) throws BusinessException {
        logger.info("后端接收到查询请求"+"查询股票"+stockId+"查询用户"+userId);
        RealTime1 realTime1 = realTime1Redis.get(String.valueOf(stockId));
        if (realTime1 == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票的实时信息不存在！");
        }
        UserFund userFund = userFundRepository.findByUser_UserId(userId);
        if (userFund == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"执行交易的用户不存在！");
        }
        Stock stock = stockRepository.findById(stockId).get();
        if (stock == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票不存在！");
        }

        return CommonReturnType.success(createStockTradeVO(userFund,realTime1,stock));
    }



    //创建StockTradeVO的方法
    public StockTradeVO createStockTradeVO(UserFund userFund,RealTime1 realTime1,Stock stock) {
        StockTradeVO stockTradeVO = new StockTradeVO();
        stockTradeVO.setUserMoney(userFund.getBalance());
        stockTradeVO.setStockId(realTime1.getStockId());
        stockTradeVO.setOrderPrice(realTime1.getLastTradePrice());
        stockTradeVO.setTradeMarket(stock.getTradeMarket());
        stockTradeVO.setStockName(stock.getStockName());
        stockTradeVO.setOpenPrice(realTime1.getOpenPrice());
        return stockTradeVO;
    }


}
