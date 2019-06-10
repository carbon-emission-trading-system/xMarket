package com.stock.xMarket.service;

import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.model.RealTime1;
import com.stock.xMarket.model.RealTime2;

public interface RealTimeService {

	void sendRealTime();

	//界面中某只个股点进去 展示个股的实时信息
	RealTimeVO findRealTime(int stockId);

	List<RealTimeVO> finalRealTime(List<RealTime1> list1, List<RealTime2> list2);



}
