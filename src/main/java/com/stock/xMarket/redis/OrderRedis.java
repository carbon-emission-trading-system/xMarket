package com.stock.xMarket.redis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.stock.xMarket.VO.OrderVO;

@Repository
public class OrderRedis extends BaseRedis<List<OrderVO>>{

	private static final String REDIS_KEY = "com.xmarket.order.redis.OrderRedis";
	
	@Override
	protected String getRedisKey() {
		return REDIS_KEY;
	}

}