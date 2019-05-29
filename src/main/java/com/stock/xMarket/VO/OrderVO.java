package com.stock.xMarket.VO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class OrderVO implements Serializable{
	

	public OrderVO(){}

	private int OrderId;

	
	private int stockId;

	private int userId;

	private Time localTime;
	
	private int type;
	
	private Date date;
	
	private int tradeStraregy;
	
	private int orderAmount;
	
	private int exchangeAmount;
	
	private double orderPrice;
	
	private double exchangeAveragePrice;

	private int cancel_number;

	public int getOrderId() {
		return OrderId;
	}

	public void setOrderId(int orderId) {
		OrderId = orderId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Time getLocalTime() {
		return localTime;
	}

	public void setLocalTime(Time localTime) {
		this.localTime = localTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(int exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public double getExchangeAveragePrice() {
		return exchangeAveragePrice;
	}

	public void setExchangeAveragePrice(double exchangeAveragePrice) {
		this.exchangeAveragePrice = exchangeAveragePrice;
	}

	public int getCancel_number() {
		return cancel_number;
	}

	public void setCancel_number(int cancel_number) {
		this.cancel_number = cancel_number;
	}

	public int getTradeStraregy() {
		return tradeStraregy;
	}

	public void setTradeStraregy(int tradeStraregy) {
		this.tradeStraregy = tradeStraregy;
	}

	
	
	
	
	
}