package com.stock.xMarket.VO;

import java.io.Serializable;
import java.util.Date;

public class OrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;//委托日期
	private Date localTime;//委托时间
    private int stockId;//股票代码
	private String stockName;//股票简称
	private int type;//操作：买入卖出
	private int orderAmount;//委托数量
	private int exchangeAmount;//成交数量
	private double exchangeAveragePrice;//成交均价
	private double orderPrice;//委托价格
	private int cancelNumber;//撤单数量
	private int tradeStraregy;//订单类型（市价委托、限价委托）
	private int orderId;//合同编号

	
	public OrderVO(Date date, Date localTime, int stockId, String stockName, int type, int orderAmount,int exchangeAmount, double exchangeAveragePrice, double orderPrice, int cancelNumber, int tradeStraregy,int orderId) {
		super();
		this.date = date;
		this.localTime = localTime;
		this.stockId = stockId;
		this.stockName = stockName;
		this.type = type;
		this.orderAmount = orderAmount;
		this.exchangeAmount = exchangeAmount;
		this.exchangeAveragePrice = exchangeAveragePrice;
		this.orderPrice = orderPrice;
		this.cancelNumber = cancelNumber;
		this.tradeStraregy = tradeStraregy;
		this.orderId = orderId;
	}
	
	public OrderVO() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getLocalTime() {
		return localTime;
	}

	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public double getExchangeAveragePrice() {
		return exchangeAveragePrice;
	}

	public void setExchangeAveragePrice(double exchangeAveragePrice) {
		this.exchangeAveragePrice = exchangeAveragePrice;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
}
