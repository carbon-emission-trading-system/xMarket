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
	
	
	public TradeOrder(int tradeOrderId, String stockID, int buyOrderId, int sellOrderId, boolean sellPoint,
			boolean buyPoint, Time time, Date date, double tradePrice, int exchangeAmount, boolean tradeType) {
		super();
		this.tradeOrderId = tradeOrderId;
		this.stockID = stockID;
		this.buyOrderId = buyOrderId;
		this.sellOrderId = sellOrderId;
		this.sellPoint = sellPoint;
		this.buyPoint = buyPoint;
		this.time = time;
		this.date = date;
		this.tradePrice = tradePrice;
		this.exchangeAmount = exchangeAmount;
		this.tradeType = tradeType;
	}
	
	
	public TradeOrder() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int tradeOrderId;
	@Column
	private String stockID;
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

	public int getTrade_order_id() {
		return tradeOrderId;
	}
	public void setTrade_order_id(int trade_order_id) {
		this.tradeOrderId = trade_order_id;
	}
	public int getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
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
	
	public int getTradeOrderId() {
		return tradeOrderId;
	}


	public void setTradeOrderId(int tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
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

	public double getTotalExchangeMoney() {
		return totalExchangeMoney;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}

	public void setTotalExchangeMoney() {
		this.totalExchangeMoney = this.exchangeAmount*this.tradePrice;
	}

}
