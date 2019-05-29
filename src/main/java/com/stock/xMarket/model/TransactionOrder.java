package com.stock.xMarket.model;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transaction_order")
public class TransactionOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_order_id")
	private int transactionOrderId;
	
	@Column(name="stock_id")
	private int stockId;
	
	@Column(name="stock_name")
	private String stockName;
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name="point")
	private boolean point;//买卖标识,true是买
	
	@Column(name="order_id")
	private int orderId;//委托单id
	
	@Column(name="owner_id")
	private int ownerId;//拥有者id
	
	@Column(name="time")
	private Time time;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="trade_price")
	private double tradePrice;//成交价
	
	@Column(name="exchange_amount")
	private int exchangeAmount;//成家数量
	
	@Column(name="trade_type")
	private Boolean tradeType;//买/卖盘    ture是买盘
	
	@Column(name="service_tax")
	private double serviceTax;//手续费
	
	@Column(name="stamp_tax")
	private double stampTax;//印花税
	
	@Column(name="other_fee")
	private double otherFee;//其他杂费
	
	@Column(name="actual_amount")
	private double actualAmount;//发生金额
	
	@Column(name="trade_market")
	private double tradeMarket;//交易市场

	@Transient
	private double totalExchangeMoney;

	public double getTotalExchangeMoney() {
		return totalExchangeMoney;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}

	public int getTransactionOrderId() {
		return transactionOrderId;
	}

	public void setTransactionOrderId(int transactionOrderId) {
		this.transactionOrderId = transactionOrderId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public boolean isPoint() {
		return point;
	}

	public void setPoint (boolean point) {
		this.point = point;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public int getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(int exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public Boolean getTradeType() {
		return tradeType;
	}

	public void setTradeType(Boolean tradeType) {
		this.tradeType = tradeType;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getStampTax() {
		return stampTax;
	}

	public void setStampTax(double stampTax) {
		this.stampTax = stampTax;
	}

	public double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public double getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(double tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}