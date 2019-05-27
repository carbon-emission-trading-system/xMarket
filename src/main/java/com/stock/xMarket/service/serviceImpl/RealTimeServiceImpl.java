package com.stock.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.config.RabbitMqConfig;
import com.stock.xMarket.service.RealTimeService;

import org.springframework.core.env.Environment;




@Service
@Transactional
public class RealTimeServiceImpl implements RealTimeService{

	private Environment env;
	
	@Autowired
	AmqpAdmin amqpAdmin;
	
	@Autowired
	RabbitMqConfig rabbitmqConfig;

	@Override
	public void createConn() {
		// TODO Auto-generated method stub
		Queue queue=new Queue("realTimeQueue");
		amqpAdmin.declareQueue(queue);
		
		amqpAdmin.declareBinding(new Binding("realTimeQueue", Binding.DestinationType.QUEUE, "realTimeExchange", null, null));
		
//		BindingBuilder.bind(queue).to("").with();
		
		
	}
}
