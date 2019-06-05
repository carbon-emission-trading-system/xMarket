package com.stock.xMarket.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.xMarket.VO.RealTimeVO;

public interface RealTimeService {

	void sendRealTime();

	//界面中某只个股点进去 展示个股的实时信息
	RealTimeVO findRealTime(int stockId);



}
