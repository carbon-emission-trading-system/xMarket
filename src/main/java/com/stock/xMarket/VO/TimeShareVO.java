package com.stock.xMarket.VO;

import java.sql.Time;

public class TimeShareVO {
	private String stockID;
	private Time realTime;
	private double latestPrice;
	private double averagePrice;
	private int volume;
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public Time getRealTime() {
		return realTime;
	}
	public void setRealTime(Time realTime) {
		this.realTime = realTime;
	}
	public double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	
	
}
