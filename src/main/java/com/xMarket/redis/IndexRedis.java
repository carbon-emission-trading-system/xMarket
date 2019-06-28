package com.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.xMarket.VO.IndexVO;
import com.xMarket.model.Index;
import com.xMarket.model.Order;

@Repository
public class IndexRedis extends BaseRedis<IndexVO> {

	private static final String REDIS_KEY = "com.xmarket.redis.IndexRedis";
	
	@Override
	protected String getRedisKey() {
		// TODO Auto-generated method stub
		return REDIS_KEY;
	}

}
