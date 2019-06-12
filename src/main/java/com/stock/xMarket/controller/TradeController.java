package com.stock.xMarket.controller;

import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.repository.HoldPositionRepository;
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
    private StockRepository stockRepository;

    @Autowired
    private HoldPositionRepository holdPositionRepository;

    final static Logger logger=LoggerFactory.getLogger(TradeController.class);

    @RequestMapping(value = "/QueryStockInformation", method = RequestMethod.GET)
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
        HoldPosition holdPosition = holdPositionRepository.findByUser_UserId(userId);


        return CommonReturnType.success(createStockTradeVO(userFund,realTime1,stock,holdPosition));
//        StockTradeVO stockTradeVO = new StockTradeVO();
//        stockTradeVO.setAvailableNumber(10000);
//        stockTradeVO.setOpenPrice(100);
//        stockTradeVO.setStockName("浦发银行");
//        stockTradeVO.setTradeMarket(1);
//        stockTradeVO.setOrderPrice(99);
//        stockTradeVO.setStockId(600000);
//        stockTradeVO.setBalance(100000);
//        return CommonReturnType.success(stockTradeVO);
    }



    //创建StockTradeVO的方法
    public StockTradeVO createStockTradeVO(UserFund userFund,RealTime1 realTime1,Stock stock,HoldPosition holdPosition) {
        StockTradeVO stockTradeVO = new StockTradeVO();
        stockTradeVO.setBalance(userFund.getBalance());
        stockTradeVO.setStockId(realTime1.getStockId());
        stockTradeVO.setOrderPrice(realTime1.getLastTradePrice());
        stockTradeVO.setTradeMarket(stock.getTradeMarket());
        stockTradeVO.setStockName(stock.getStockName());
        if (holdPosition == null){
            stockTradeVO.setAvailableNumber(0);
        }else {
            stockTradeVO.setAvailableNumber(holdPosition.getAvailableNumber());
        }
        stockTradeVO.setYesterdayClosePrice(realTime1.getYesterdayClosePrice());
        return stockTradeVO;
    }


}
