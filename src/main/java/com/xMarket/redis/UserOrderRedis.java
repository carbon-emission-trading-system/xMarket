package com.xMarket.redis;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.xMarket.model.Order;

@Repository
public class UserOrderRedis extends BaseRedis<ArrayList<String>>{

	private static final String REDIS_KEY = "com.order.redis.UserOrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}