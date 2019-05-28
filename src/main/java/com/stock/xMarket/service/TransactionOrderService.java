package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.TransactionOrderProjection;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;

public interface TransactionOrderService {

	List<TransactionOrderProjection> findByOwnerId(int ownerId);

	void transaction(TradeOrder tradeOrder) throws BusinessException;
}
