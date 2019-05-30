package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;

public interface OrderService {

	List<OrderVO> findByUserId(int userId);

	void updateOrderByTradeOrder(TradeOrder tradeOrder);

	void addOrderToRedis(Order order);
}
