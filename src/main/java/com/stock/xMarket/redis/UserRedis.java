package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.User;



@Repository
public class UserRedis extends BaseRedis<User>{

	private static final String REDIS_KEY = "com.dayup.seckil.redis.UserRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}
