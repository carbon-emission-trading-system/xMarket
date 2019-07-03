package com.xMarket.VO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


public class OrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;//委托日期
	private Time time;//委托时间
    private String stockId;//股票代码
    private int userId;
	private String stockName;//股票简称
	private int type;//操作：买入卖出
	private int orderAmount;//委托数量
	private int exchangeAmount;//成交数量
	private double exchangeAveragePrice;//成交均价
	private double orderPrice;//委托价格
	private int cancelNumber;//撤单数量
	private int tradeStraregy;//订单类型（市价委托、限价委托）
	private int state;
	private String orderId;//合同编号
	private double frozenAmount;


	
	
	


	public OrderVO() {
	}
	
	public OrderVO(String orderId) {
		this.orderId=orderId;
	}

	
	
	
	
	





	public OrderVO(Date date, Time time, String stockId, int userId, String stockName, int type, int orderAmount,
			int exchangeAmount, double exchangeAveragePrice, double orderPrice, int cancelNumber, int tradeStraregy,
			int state, String orderId, double frozenAmount) {
		super();
		this.date = date;
		this.time = time;
		this.stockId = stockId;
		this.userId = userId;
		this.stockName = stockName;
		this.type = type;
		this.orderAmount = orderAmount;
		this.exchangeAmount = exchangeAmount;
		this.exchangeAveragePrice = exchangeAveragePrice;
		this.orderPrice = orderPrice;
		this.cancelNumber = cancelNumber;
		this.tradeStraregy = tradeStraregy;
		this.state = state;
		this.orderId = orderId;
		this.frozenAmount = frozenAmount;
	}

	public double getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	public Time getTime() {
		return time;
	}



	public void setTime(Time time) {
		this.time = time;
	}



	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}





	
	
}
