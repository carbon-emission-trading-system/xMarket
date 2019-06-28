package com.xMarket.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface SystemService {

	public void initialRealTime();

	public void addStockHistory();

	public void cleanOrder();

	public void unfreeze();

	public void addUserFundHistory();

	public void clear();

}
