package com.xMarket.redis;

import org.springframework.stereotype.Repository;

import com.xMarket.model.TransactionOrder;

@Repository
public class TransactionRedis extends BaseRedis<TransactionOrder> {

    private static final String REDIS_KEY = "com.xMarket.redis.TransactionRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

}
