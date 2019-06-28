package com.xMarket.service;

import com.xMarket.VO.OrderVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.Order;
import com.xMarket.model.TransactionOrder;

public interface UserFundService {


	public void updateUserFundByOrder(Order order) throws BusinessException;

	void updateUserFundByTransaction(TransactionOrder transactionOrder);

	double frozenAmountCaculator(Order order);

}
