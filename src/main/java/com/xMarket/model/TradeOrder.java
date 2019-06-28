package com.xMarket.model;


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
	
	
	
	
	public TradeOrder() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private long tradeOrderId;
	@Column
	private String stockId;
	@Column
	private long buyOrderId;
	@Column
	private long sellOrderId;
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
	private int sellerId;

	@Transient
	private int buyerId;

	@Column(name="trade_market")
	private int tradeMarket;//交易市场   market


	


	public TradeOrder(long tradeOrderId, String stockId, long buyOrderId, long sellOrderId, boolean sellPoint,
			boolean buyPoint, Time time, Date date, double tradePrice, int exchangeAmount, boolean tradeType,
			double totalExchangeMoney, int sellerId, int buyerId, int tradeMarket) {
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
		this.totalExchangeMoney = totalExchangeMoney;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.tradeMarket = tradeMarket;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	

	public long getTradeOrderId() {
		return tradeOrderId;
	}

	public void setTradeOrderId(long tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}

	public long getBuyOrderId() {
		return buyOrderId;
	}

	public void setBuyOrderId(long buyOrderId) {
		this.buyOrderId = buyOrderId;
	}

	public long getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(long sellOrderId) {
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

	

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}

	public void setTotalExchangeMoney() {
		this.totalExchangeMoney = this.exchangeAmount*this.tradePrice;
	}

}
