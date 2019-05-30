package com.stock.xMarket.service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TransactionOrder;

public interface UserFundService {


	public void updateUserFundByOrder(Order order);

	void updateUserFundByTransaction(TransactionOrder transactionOrder);

}
