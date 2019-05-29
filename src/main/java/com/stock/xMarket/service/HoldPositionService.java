package com.stock.xMarket.service;

import com.xmarket.order.VO.OrderVO;
import com.xmarket.order.error.BusinessException;
import com.xmarket.order.model.TransactionOrder;

public interface HoldPositionService {

	void updateHoldPosition(TransactionOrder transactionOrder) throws BusinessException;

	void updateAvailable(OrderVO orderVO) throws BusinessException;
}
