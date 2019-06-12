package com.stock.xMarket.service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TransactionOrder;

public interface UserFundService {


	public void updateUserFundByOrder(Order order) throws BusinessException;

	void updateUserFundByTransaction(TransactionOrder transactionOrder);

}
