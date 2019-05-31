package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;

public interface OrderService {

	List<OrderVO> findByUserId(int userId);


	void addOrderToDb(Order order);
	
	void addOrderToRedis(Order order);
	
	void updateOrderByTradeOrder(TransactionOrder revokeOrder);

}
