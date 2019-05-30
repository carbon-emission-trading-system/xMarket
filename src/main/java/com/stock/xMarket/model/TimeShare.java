package com.stock.xMarket.model;

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
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="stock_id")
	private Stock stock;


	
	@Column
	private Time realTime;
	
	@Column
	private double latestPrice;
	
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


	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
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
