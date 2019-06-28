package com.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.xMarket.model.RealTime2;


@Repository
public class RealTime2Redis extends BaseRedis<RealTime2>{

	private static final String REDIS_KEY = "com.xMarket.redis.RealTime2Redis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}