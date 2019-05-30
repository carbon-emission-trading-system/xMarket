package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.RealTimeVO;
import com.stock.xMarket.VO.TradeOrderVO;
import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.controller.listener.OrderListener;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.repository.TradeOrderRepository;
import com.stock.xMarket.service.RealTimeService;
import com.stock.xMarket.service.TimeShareService;
import com.stock.xMarket.service.TradeOrderService;
import com.stock.xMarket.service.TransactionOrderService;



@Service
@Transactional
public class TradeOrderServiceImpl implements TradeOrderService {


    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);
    
    
  
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Autowired
	RabbitMqConfig rabbitmqConfig;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	TradeOrderRepository tradeOrderRepository;

	
	@Autowired
	TimeShareService timeShareService;
	


	@Override
	public void saveTradeOrder(TradeOrder tradeOrder) {
		
		tradeOrderRepository.saveAndFlush(tradeOrder);
	
		
	}

}
