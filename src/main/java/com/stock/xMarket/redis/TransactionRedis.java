package com.stock.xMarket.redis;

import org.springframework.stereotype.Repository;
import com.stock.xMarket.model.TransactionOrder;

@Repository
public class TransactionRedis extends BaseRedis<TransactionOrder> {

    private static final String REDIS_KEY = "com.xmarket.order.redis.TransactionTimerRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

}
