package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.VO.StockTradeVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.model.UserFund;

public interface OrderService {

	List<Order> findByUserId(int userId);


	void addOrderToDb(Order order);
	
	void addOrderToRedis(Order order);
	
 	void buyOrSale(OrderVO orderVO) throws BusinessException;

	void updateOrderBytransactionOrder(TransactionOrder transactionOrder);


	void sendCallOrder();


	void sendCancelOrder(long orderId) throws BusinessException;


	StockTradeVO createStockTradeVO(UserFund userFund, RealTime1 realTime1, Stock stock, HoldPosition holdPosition);


}
