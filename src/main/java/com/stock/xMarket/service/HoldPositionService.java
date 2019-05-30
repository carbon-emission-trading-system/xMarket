package com.stock.xMarket.service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.TransactionOrder;
import com.xmarket.order.model.Order;

public interface HoldPositionService {


	void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException;

	void updateHoldPositionByOrder(Order order) throws BusinessException;
}
