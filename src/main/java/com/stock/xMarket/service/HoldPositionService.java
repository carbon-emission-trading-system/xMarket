package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.HoldPositionVO;
import com.stock.xMarket.VO.UserFundVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TransactionOrder;


public interface HoldPositionService {


	void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException;

	void updateHoldPositionByOrder(Order order) throws BusinessException;

	void updateHoldPositionByCancelOrder(TransactionOrder cancelOrder) throws BusinessException;
	//根据用户id找到用户持仓股票
	List<HoldPositionVO> findHoldPosition(int userId);

	//根据用户id找到用户资产信息
	UserFundVO getFunds(int userId);
}
