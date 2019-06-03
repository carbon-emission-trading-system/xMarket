package com.stock.xMarket.redis;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Order;

@Repository
public class UserOrderRedis extends BaseRedis<ArrayList<String>>{

	private static final String REDIS_KEY = "com.xmarket.order.redis.UserOrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}