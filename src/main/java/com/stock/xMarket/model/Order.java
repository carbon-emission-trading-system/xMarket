package com.stock.xMarket.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="commission_order")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="stock_id")
	private Stock stock;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="local_time")
	private Time localTime;
	
	@Column(name="type")
	private int type;//买入卖出
	
	@Column(name="date")
	private Date date;
	
	@Column(name="trade_straregy")
	private int tradeStraregy;
	
	@Column(name="order_amount")
	private int orderAmount;

	@Column(name="exchange_amount")
	private int exchangeAmount;
	
	@Column(name="order_price")
	private double orderPrice;
	
	@Column(name="exchange_average_price")
	private double exchangeAveragePrice;

	@Column(name="cancel_number")
	private int cancelNumber;

	public Order(){}

	public Order(int orderId, Stock stock, User user, Time localTime, int type, Date date, int tradeStraregy,
			int orderAmount, int exchangeAmount, double orderPrice, double exchangeAveragePrice, int cancelNumber) {
		super();
		this.orderId = orderId;
		this.stock = stock;
		this.user = user;
		this.localTime = localTime;
		this.type = type;
		this.date = date;
		this.tradeStraregy = tradeStraregy;
		this.orderAmount = orderAmount;
		this.exchangeAmount = exchangeAmount;
		this.orderPrice = orderPrice;
		this.exchangeAveragePrice = exchangeAveragePrice;
		this.cancelNumber = cancelNumber;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public int getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(int cancelNumber) {
		this.cancelNumber = cancelNumber;
	}

	public int getTradeStraregy() {
		return tradeStraregy;
	}

	public void setTradeStraregy(int tradeStraregy) {
		this.tradeStraregy = tradeStraregy;
	}
	
}