package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;
import com.stock.xMarket.model.TransactionOrder;

@Repository
public class TransactionRedis extends BaseRedis<TransactionOrder> {

    private static final String REDIS_KEY = "com.stock.xMarket.redis.TransactionRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

}
