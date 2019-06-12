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
	private long totalShareCapital; //总股本
	private double bookValue; //每股净资
	private int tradeMarket; //0--深A，1--沪A
	
	public RealTime2() {
	}
	public RealTime2(int stockId, String stockName, double earningsPerShare, double yesterdayOpenPrice,
			 long totalShareCapital, double bookValue, int tradeMarket) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.earningsPerShare = earningsPerShare;
		this.yesterdayOpenPrice = yesterdayOpenPrice;
		this.totalShareCapital = totalShareCapital;
		this.bookValue = bookValue;
		this.tradeMarket = tradeMarket;
	}
	public RealTime2(int stockId) {
		// TODO Auto-generated constructor stub
		this.stockId = stockId;
	}
	public RealTime2(Stock stock) {
		// TODO Auto-generated constructor stub
		this.stockId=stock.getStockId();
		this.stockName=stock.getStockName();
		this.earningsPerShare=stock.getEarningsPerShare();
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
	public int getTradeMarket() {
		return tradeMarket;
	}
	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
