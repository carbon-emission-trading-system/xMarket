package com.stock.xMarket.service;

import java.util.List;

import com.stock.xMarket.VO.OrderVO;

public interface OrderService {

	List<OrderVO> findByUserId(int userId);
}
