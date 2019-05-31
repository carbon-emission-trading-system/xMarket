package com.stock.xMarket.model;


import org.springframework.data.annotation.Transient;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class TradeOrder {
	
	
	public TradeOrder(int tradeOrderId, int stockId, int buyOrderId, int sellOrderId,boolean buyPoint, boolean sellPoint,
			 Time time, Date date, double tradePrice, int exchangeAmount, boolean tradeType,
			  int buyId,int sellId) {
		super();
		this.tradeOrderId = tradeOrderId;
		this.stockId = stockId;
		this.buyOrderId = buyOrderId;
		this.sellOrderId = sellOrderId;
		this.sellPoint = sellPoint;
		this.buyPoint = buyPoint;
		this.time = time;
		this.date = date;
		this.tradePrice = tradePrice;
		this.exchangeAmount = exchangeAmount;
		this.tradeType = tradeType;
		this.sellId = sellId;
		this.buyId = buyId;
	}
	
	public TradeOrder() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int tradeOrderId;
	@Column
	private int stockId;
	@Column
	private int buyOrderId;
	@Column
	private int sellOrderId;
	@Column
	private boolean sellPoint;
	@Column
	private boolean buyPoint;
	@Column
	private Time time;
	@Column
	private Date date;
	@Column
	private double tradePrice;
	@Column
	private int exchangeAmount;
	@Column
	private boolean tradeType;

	@Transient
	private double totalExchangeMoney;

	@Transient
	private int sellId;

	@Transient
	private int buyId;

	@Column(name="trade_market")
	private int tradeMarket;//交易市场   market

	public int getTradeOrderId() {
		return tradeOrderId;
	}

	public void setTradeOrderId(int tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getBuyOrderId() {
		return buyOrderId;
	}

	public void setBuyOrderId(int buyOrderId) {
		this.buyOrderId = buyOrderId;
	}

	public int getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(int sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public boolean isSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(boolean sellPoint) {
		this.sellPoint = sellPoint;
	}

	public boolean isBuyPoint() {
		return buyPoint;
	}

	public void setBuyPoint(boolean buyPoint) {
		this.buyPoint = buyPoint;
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

	public boolean isTradeType() {
		return tradeType;
	}

	public void setTradeType(boolean tradeType) {
		this.tradeType = tradeType;
	}

	public double getTotalExchangeMoney() {
		return totalExchangeMoney;
	}

	public int getSellId() {
		return sellId;
	}

	public void setSellId(int sellId) {
		this.sellId = sellId;
	}

	public int getBuyId() {
		return buyId;
	}

	public void setBuyId(int buyId) {
		this.buyId = buyId;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}

	public void setTotalExchangeMoney() {
		this.totalExchangeMoney = this.exchangeAmount*this.tradePrice;
	}

}
