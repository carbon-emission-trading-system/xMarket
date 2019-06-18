package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.VO.IndexVO;
import com.stock.xMarket.model.Index;
import com.stock.xMarket.model.Order;

@Repository
public class IndexRedis extends BaseRedis<IndexVO> {

	private static final String REDIS_KEY = "com.xmarket.order.redis.IndexRedis";
	
	@Override
	protected String getRedisKey() {
		// TODO Auto-generated method stub
		return REDIS_KEY;
	}

}
