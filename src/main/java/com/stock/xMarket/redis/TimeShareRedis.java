package com.stock.xMarket.redis;

import com.stock.xMarket.model.RealTime1;
import org.springframework.stereotype.Repository;

@Repository
public class TimeShareRedis  extends BaseRedis<Integer>{


	private static final String REDIS_KEY = "com.stock.xMarket.redis.TimeShareRedis";
	
	@Override
	protected String getRedisKey() {
		// TODO Auto-generated method stub
		
		
		return REDIS_KEY;
	}

}
