package com.stock.xMarket.service;

import java.util.List;
import com.stock.xMarket.VO.TransactionOrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;

public interface TransactionOrderService {

	List<TransactionOrderVO> findByOwnerId(int ownerId);

	void addTransactionOrder(TradeOrder tradeOrder) throws BusinessException;

	List<TransactionOrder> findByOwnerIdAndDate(int userId) throws BusinessException;

}
