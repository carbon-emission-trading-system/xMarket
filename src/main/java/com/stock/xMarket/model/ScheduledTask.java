package com.stock.xMarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="scheduled_task")
public class ScheduledTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int scheduledTaskId; 
	@Column
	private String taskKey; 
	@Column
	private String taskCron;
	@Column
	private boolean initStartFlag;
	public int getScheduledTaskId() {
		return scheduledTaskId;
	}
	public void setScheduledTaskId(int scheduledTaskId) {
		this.scheduledTaskId = scheduledTaskId;
	}
	public String getTaskKey() {
		return taskKey;
	}
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	public String getTaskCron() {
		return taskCron;
	}
	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}

	public boolean isInitStartFlag() {
		return initStartFlag;
	}
	public void setInitStartFlag(boolean initStartFlag) {
		this.initStartFlag = initStartFlag;
	}
	public ScheduledTask() {
	}
	public ScheduledTask(int scheduledTaskId, String taskKey, String taskCron, boolean initStartFlag) {
		super();
		this.scheduledTaskId = scheduledTaskId;
		this.taskKey = taskKey;
		this.taskCron = taskCron;
		this.initStartFlag = initStartFlag;
	}

	

	
	

	

	
}