package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.OrderVO;
import com.xMarket.VO.StockTradeVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.HoldPosition;
import com.xMarket.model.Order;
import com.xMarket.model.RealTime1;
import com.xMarket.model.Stock;
import com.xMarket.model.TradeOrder;
import com.xMarket.model.TransactionOrder;
import com.xMarket.model.UserFund;

public interface OrderService {

	List<Order> findByUserId(int userId);


	void addOrderToDb(Order order);
	
	void addOrderToRedis(Order order);
	
 	void buyOrSale(OrderVO orderVO) throws BusinessException;

	void updateOrderBytransactionOrder(TransactionOrder transactionOrder);


	void sendCallOrder();


	void sendCancelOrder(long orderId) throws BusinessException;


	StockTradeVO createStockTradeVO(UserFund userFund, RealTime1 realTime1, Stock stock, HoldPosition holdPosition);


	void setState(String orderId);


}
