package com.stock.xMarket.service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.TransactionOrder;

public interface HoldPositionService {

	void updateHoldPositionByOrder(OrderVO orderVO) throws BusinessException;

	void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException;
}
