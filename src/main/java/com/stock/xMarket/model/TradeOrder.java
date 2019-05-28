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
	
	
	public TradeOrder(String tradeOrderId, String stockID, String buyOrderId, String sellOrderId, boolean sellPoint,
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
	private String tradeOrderId;
	@Column
	private String stockID;
	@Column
	private String buyOrderId;
	@Column
	private String sellOrderId;
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

	public String getTrade_order_id() {
		return tradeOrderId;
	}
	public void setTrade_order_id(String trade_order_id) {
		this.tradeOrderId = trade_order_id;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getBuyOrderId() {
		return buyOrderId;
	}
	public void setBuyOrderId(String buyOrderId) {
		this.buyOrderId = buyOrderId;
	}
	public String getSellOrderId() {
		return sellOrderId;
	}
	public void setSellOrderId(String sellOrderId) {
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
	
	public String getTradeOrderId() {
		return tradeOrderId;
	}


	public void setTradeOrderId(String tradeOrderId) {
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
