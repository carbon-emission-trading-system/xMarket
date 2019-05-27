package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.User;


@Repository
public class RealTimeRedis extends BaseRedis<User>{

	private static final String REDIS_KEY = "com.xmarket.order.redis.RealTimerRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}