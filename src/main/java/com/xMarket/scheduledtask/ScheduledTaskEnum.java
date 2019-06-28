package com.xMarket.scheduledtask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务枚举值
 * 注：key 需要与数据库保持一致
 */
public enum ScheduledTaskEnum {

    /**
     * 任务1
     */
    TASK_01("scheduledTask01", new ScheduledTask01()),
//    /**
//     * 任务2
//     */
//    TASK_02("scheduledTask02", new ScheduledTask02()),
//    /**
//     * 任务3
//     */
//    TASK_03("scheduledTask03", new ScheduledTask03()),
    ;
    /**
     * 定时任务key
     */
    private String taskKey;
    /**
     * 定时任务 执行实现类
     */
    private ScheduledTaskJob scheduledTaskJob;

    ScheduledTaskEnum(String taskKey, ScheduledTaskJob scheduledTaskJob) {
        this.taskKey = taskKey;
        this.scheduledTaskJob = scheduledTaskJob;
    }

    /**
     * 初始化 所有任务
     */
    public static Map<String, ScheduledTaskJob> initScheduledTask() {
        if (ScheduledTaskEnum.values().length < 0) {
            return new ConcurrentHashMap<>();
        }
        Map<String, ScheduledTaskJob> scheduledTaskJobMap = new ConcurrentHashMap<>();
        for (ScheduledTaskEnum taskEnum : ScheduledTaskEnum.values()) {
            scheduledTaskJobMap.put(taskEnum.taskKey, taskEnum.scheduledTaskJob);
        }
        return scheduledTaskJobMap;
    }
}