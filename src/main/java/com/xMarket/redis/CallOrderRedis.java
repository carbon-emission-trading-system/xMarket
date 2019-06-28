package com.xMarket.redis;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.xMarket.model.Order;

@Repository
public class CallOrderRedis extends BaseRedis<ArrayList<Order>>{

	private static final String REDIS_KEY = "com.xmarket.redis.CallOrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}