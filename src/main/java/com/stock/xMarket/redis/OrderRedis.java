package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Order;

@Repository
public class OrderRedis extends BaseRedis<Order>{

	private static final String REDIS_KEY = "com.xmarket.order.redis.OrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}