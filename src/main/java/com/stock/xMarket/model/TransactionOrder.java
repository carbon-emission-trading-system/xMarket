package com.stock.xMarket.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="transaction_order")
public class TransactionOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_order_id")
	private long transactionOrderId;
	
	@Column(name="stock_id")
	private int stockId;
	
	@Column(name="stock_name")
	private String stockName;

	@Column(name="type")
	private int type;//买卖标识  1是卖
	
	@Column(name="order_id")
	private long orderId;//委托单id  sellOrderId
	
	@Column(name="owner_id")
	private int ownerId;//拥有者id
	
	@Column(name="time")
	private Time time;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="trade_price")
	private double tradePrice;//成交价
	
	@Column(name="exchange_amount")
	private int exchangeAmount;//成交数量
	
	@Column(name="trade_type")
	private Boolean tradeType;//买/卖盘    ture是买盘
	
	@Column(name="service_tax")
	private double serviceTax;//手续费
	
	@Column(name="stamp_tax")
	private double stampTax;//印花税
	
	@Column(name="other_fee")
	private double otherFee;//其他杂费   incidentalCharge
	
	@Column(name="actual_amount")
	private double actualAmount;//发生金额
	
	@Column(name="trade_market")
	private int tradeMarket;//交易市场   market
	
	@Column(name="stock_balance")
	private int stockBalance;//股票余额

	@Column(name="revoke_amount")
	private int cancelNumber;//撤单数量

	@Transient
	private double totalExchangeMoney;// exchangeFee

	
	
	
	public TransactionOrder(long transactionOrderId, int stockId, String stockName, int type, long orderId, int ownerId,
			Time time, Date date, double tradePrice, int exchangeAmount, Boolean tradeType, double serviceTax,
			double stampTax, double otherFee, double actualAmount, int tradeMarket, int stockBalance, int cancelNumber,
			double totalExchangeMoney) {
		super();
		this.transactionOrderId = transactionOrderId;
		this.stockId = stockId;
		this.stockName = stockName;
		this.type = type;
		this.orderId = orderId;
		this.ownerId = ownerId;
		this.time = time;
		this.date = date;
		this.tradePrice = tradePrice;
		this.exchangeAmount = exchangeAmount;
		this.tradeType = tradeType;
		this.serviceTax = serviceTax;
		this.stampTax = stampTax;
		this.otherFee = otherFee;
		this.actualAmount = actualAmount;
		this.tradeMarket = tradeMarket;
		this.stockBalance = stockBalance;
		this.cancelNumber = cancelNumber;
		this.totalExchangeMoney = totalExchangeMoney;
	}

	
	
	public TransactionOrder() {
	}



	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	public double getTotalExchangeMoney() {
		return totalExchangeMoney;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}

	

	public long getTransactionOrderId() {
		return transactionOrderId;
	}

	public void setTransactionOrderId(long transactionOrderId) {
		this.transactionOrderId = transactionOrderId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	public int getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(int stockBalance) {
		this.stockBalance = stockBalance;
	}

	public int getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(int cancelNumber) {
		this.cancelNumber = cancelNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}
	
	
	
}
