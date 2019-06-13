package com.stock.xMarket.VO;

import java.sql.Date;
import java.sql.Time;

public class TimeShareVO {
	private int stockId;
	private Time realTime;
	private double lastTradePrice;
	private double averagePrice;
	private int volume;
	private Date date;
	
	

	public double getLastTradePrice() {
		return lastTradePrice;
	}
	
	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public Time getRealTime() {
		return realTime;
	}
	public void setRealTime(Time realTime) {
		this.realTime = realTime;
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
