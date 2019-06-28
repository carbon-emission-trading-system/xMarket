package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.TransactionOrderVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.TradeOrder;
import com.xMarket.model.TransactionOrder;

public interface TransactionOrderService {

	List<TransactionOrderVO> findByOwnerId(int ownerId);

	void addTransactionOrder(TradeOrder tradeOrder) throws BusinessException;

	List<TransactionOrder> findByOwnerIdAndDate(int userId) throws BusinessException;
}
