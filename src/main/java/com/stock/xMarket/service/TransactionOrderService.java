package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.TransactionOrderProjection;
import com.stock.xMarket.model.TransactionOrder;

public interface TransactionOrderService {

	List<TransactionOrderProjection> findByOwnerId(int ownerId);
}
