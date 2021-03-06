package com.xMarket.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="time_share")
public class TimeShare {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int timeShareId;
	
	
	@Column
	private String stockId;

	@Column
	private Date date;

	
	@Column
	private Time realTime;
	
	@Column
	private double lastTradePrice;
	
	@Column
	private double averagePrice;
	
	@Column
	private int volume;
	
	
	public int getTimeShareId() {
		return timeShareId;
	}
	public void setTimeShareId(int timeShareId) {
		this.timeShareId = timeShareId;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public Time getRealTime() {
		return realTime;
	}
	public void setRealTime(Time realTime) {
		this.realTime = realTime;
	}

	public double getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
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
