package com.stock.xMarket.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;

@Service
public class MarchService {

	
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Autowired
	RabbitMqConfig rabbitmqConfig;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	
	
	public void march(Order order) {
		List<TradeOrder> tradeOrderList = new ArrayList<>();
//		tradeOrderList.add(new TradeOrder(1,600000,100,200,true,false,order.getLocalTime(),order.getDate(),10.50,300,true,11,20));
//		tradeOrderList.add(new TradeOrder(2,600000,300,200,true,true,order.getLocalTime(),order.getDate(),10.50,200,true,30,11)); 
		//sendTradeOrder(tradeOrderList);

	}
	
	public void cancel(int orderId) {
	

	}
	
	public void sendTradeOrder(List<TradeOrder> tradeOrderList) {
	// TODO Auto-generated method stub
	for(TradeOrder tradeOrder:tradeOrderList) {
		rabbitTemplate.convertAndSend("tradeOrderExchange","tradeOrderRoutingKey",tradeOrder);
	}
	
	}
}
