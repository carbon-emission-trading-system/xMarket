package com.stock.xMarket.service;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;

public interface RealTimeService {

	void sendRealTime();



}
