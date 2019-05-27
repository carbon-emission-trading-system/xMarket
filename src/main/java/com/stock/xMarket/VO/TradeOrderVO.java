package com.stock.xMarket.VO;

import java.sql.Date;
import java.sql.Time;

public class TradeOrderVO {

	
	
	
	
	public TradeOrderVO(String trade_order_id, String stockID, String buyOrderId, String sellOrderId, boolean sellPoint,
			boolean buyPoint, Time time, Date date, float tradePrice, float exchangeAmount, boolean tradeType) {
		super();
		this.trade_order_id = trade_order_id;
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
	
	
	public TradeOrderVO() {
		super();
	}


	private String trade_order_id;
	private String stockID;
	private String buyOrderId;
	private String sellOrderId;
	private boolean sellPoint;
	private boolean buyPoint;
	private Time time;
	private Date date;
	private float tradePrice;
	private float exchangeAmount;
	private boolean tradeType;
	
	
	public String getTrade_order_id() {
		return trade_order_id;
	}
	public void setTrade_order_id(String trade_order_id) {
		this.trade_order_id = trade_order_id;
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
	public float getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(float tradePrice) {
		this.tradePrice = tradePrice;
	}
	public float getExchangeAmount() {
		return exchangeAmount;
	}
	public void setExchangeAmount(float exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}
	public boolean isTradeType() {
		return tradeType;
	}
	public void setTradeType(boolean tradeType) {
		this.tradeType = tradeType;
	}

	
	
	
}
