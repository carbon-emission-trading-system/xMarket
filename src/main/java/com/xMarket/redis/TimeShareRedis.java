package com.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.xMarket.model.RealTime1;

@Repository
public class TimeShareRedis  extends BaseRedis<Integer>{


	private static final String REDIS_KEY = "com.xMarket.redis.TimeShareRedis";
	
	@Override
	protected String getRedisKey() {
		// TODO Auto-generated method stub
		
		
		return REDIS_KEY;
	}

}
