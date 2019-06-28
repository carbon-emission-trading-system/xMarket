package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.HoldPositionVO;
import com.xMarket.VO.UserFundVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.Order;
import com.xMarket.model.TransactionOrder;


public interface HoldPositionService {


	void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException;

	void updateHoldPositionByOrder(Order order) throws BusinessException;

	void updateHoldPositionByCancelOrder(TransactionOrder cancelOrder) throws BusinessException;

	//根据用户id找到用户持仓股票
	List<HoldPositionVO> findHoldPosition(int userId);

	//根据用户id找到用户资产信息
	UserFundVO getFunds(int userId);
}
