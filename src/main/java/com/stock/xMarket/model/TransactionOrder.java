package com.stock.xMarket.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
	private String transactionOrderId;
	
	@Column(name="stock_id")
	private String stockId;
	
	@Column(name="stock_name")
	private String stockName;
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name="sell_id")
	private boolean sellId;//买卖标识,true是买
	
	@Column(name="sell_order_id")
	private String sellOrderId;//委托单id
	
	@Column(name="owner_id")
	private String ownerId;//拥有者id
	
	@Column(name="time")
	private Time time;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="trade_price")
	private BigDecimal tradePrice;//成交价
	
	@Column(name="exchange_amount")
	private int exchangeAmount;//成家数量
	
	@Column(name="trade_type")
	private Boolean tradeType;//买/卖盘    ture是买盘
	
	@Column(name="service_tax")
	private BigDecimal serviceTax;//手续费
	
	@Column(name="stamp_tax")
	private BigDecimal stampTax;//印花税
	
	@Column(name="other_fee")
	private BigDecimal otherFee;//其他杂费
	
	@Column(name="actual_amount")
	private BigDecimal actualAmount;//发生金额
	
	@Column(name="trade_market")
	private BigDecimal tradeMarket;//交易市场

	public String getTransactionOrderId() {
		return transactionOrderId;
	}

	public void setTransactionOrderId(String transactionOrderId) {
		this.transactionOrderId = transactionOrderId;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public boolean isSellId() {
		return sellId;
	}

	public void setSellId(boolean sellId) {
		this.sellId = sellId;
	}

	public String getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(String sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
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

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
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

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	public BigDecimal getStampTax() {
		return stampTax;
	}

	public void setStampTax(BigDecimal stampTax) {
		this.stampTax = stampTax;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(BigDecimal tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
