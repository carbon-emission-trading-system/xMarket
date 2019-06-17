package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;

public interface OrderService {

	List<Order> findByUserId(int userId);


	void addOrderToDb(Order order);
	
	void addOrderToRedis(Order order);
	


	void updateOrderBytransactionOrder(TransactionOrder transactionOrder);


	void sendCallOrder();

}
