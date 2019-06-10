package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;
import com.stock.xMarket.model.RealTime1;

@Repository
public class RealTime1Redis  extends BaseRedis<RealTime1>{

	private static final String REDIS_KEY = "com.stock.xMarket.redis.RealTime1Redis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

} 