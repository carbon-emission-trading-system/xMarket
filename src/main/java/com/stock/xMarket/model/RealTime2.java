package com.stock.xMarket.model;

import java.io.Serializable;

public class RealTime2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stockId;
	private String stockName;
	private double earningsPerShare; //每股收益
	private double yesterdayOpenPrice; //昨日开盘价
	private double yesterdayClosePrice; //昨日收盘价
	private long totalShareCapital; //总股本
	private double bookValue; //每股净资
	private int type; //0--深A，1--沪A
	
	
	public RealTime2(int stockId, String stockName, double earningsPerShare, double yesterdayOpenPrice,
			double yesterdayClosePrice, long totalShareCapital, double bookValue, int type) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.earningsPerShare = earningsPerShare;
		this.yesterdayOpenPrice = yesterdayOpenPrice;
		this.yesterdayClosePrice = yesterdayClosePrice;
		this.totalShareCapital = totalShareCapital;
		this.bookValue = bookValue;
		this.type = type;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getEarningsPerShare() {
		return earningsPerShare;
	}
	public void setEarningsPerShare(double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}
	public double getYesterdayOpenPrice() {
		return yesterdayOpenPrice;
	}
	public void setYesterdayOpenPrice(double yesterdayOpenPrice) {
		this.yesterdayOpenPrice = yesterdayOpenPrice;
	}
	public double getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}
	public void setYesterdayClosePrice(double yesterdayClosePrice) {
		this.yesterdayClosePrice = yesterdayClosePrice;
	}
	public long getTotalShareCapital() {
		return totalShareCapital;
	}
	public void setTotalShareCapital(long totalShareCapital) {
		this.totalShareCapital = totalShareCapital;
	}
	public double getBookValue() {
		return bookValue;
	}
	public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
