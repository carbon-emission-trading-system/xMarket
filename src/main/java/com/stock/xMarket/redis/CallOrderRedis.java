package com.stock.xMarket.redis;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Order;

@Repository
public class CallOrderRedis extends BaseRedis<ArrayList<Order>>{

	private static final String REDIS_KEY = "com.xmarket.order.redis.CallOrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}