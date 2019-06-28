package com.xMarket.redis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xMarket.model.Order;

@Repository
public class OrderRedis extends BaseRedis<Order>{

	private static final String REDIS_KEY = "com.xmarket.redis.OrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}