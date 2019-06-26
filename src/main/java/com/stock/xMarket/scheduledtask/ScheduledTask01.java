package com.stock.xMarket.scheduledtask;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 任务 01
 */
public class ScheduledTask01 implements ScheduledTaskJob {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask01.class);

    @Override
    public void run() {
        LOGGER.info("ScheduledTask => 01  run  当前线程名称 {} ", Thread.currentThread().getName());
    }
}
