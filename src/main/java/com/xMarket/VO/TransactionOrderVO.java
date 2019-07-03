package com.xMarket.VO;

import java.util.Date;


public class TransactionOrderVO {
	
	
	private Date date;//成交日期
	private Date time;//成交时间
	private String stockId;
	private String stockName;
	private int type;//买入卖出  true=买入
	private double tradePrice;//成交价
	private int exchangeAmount;////成交数量
	private double totalExchangeMoney;//成交金额
	
	private long stockBalance;//股票余额
	private String orderId;//委托单id--合同编号
	private long transactionOrderId;//历史成交单id--成交编号
	
	private double serviceTax;//手续费
	private double stampTax;//印花税
	private double transferFee;//其他杂费
	private double actualAmount;//发生金额
	private int tradeMarket;//交易市场
	private int cancelNumber;//撤单数量
	


	public TransactionOrderVO() {
		
	}
	
	









	public TransactionOrderVO(Date date, Date time, String stockId, String stockName, int type, double tradePrice,
			int exchangeAmount, double totalExchangeMoney, long stockBalance, String orderId, long transactionOrderId,
			double serviceTax, double stampTax, double transferFee, double actualAmount, int tradeMarket,
			int cancelNumber) {
		super();
		this.date = date;
		this.time = time;
		this.stockId = stockId;
		this.stockName = stockName;
		this.type = type;
		this.tradePrice = tradePrice;
		this.exchangeAmount = exchangeAmount;
		this.totalExchangeMoney = totalExchangeMoney;
		this.stockBalance = stockBalance;
		this.orderId = orderId;
		this.transactionOrderId = transactionOrderId;
		this.serviceTax = serviceTax;
		this.stampTax = stampTax;
		this.transferFee = transferFee;
		this.actualAmount = actualAmount;
		this.tradeMarket = tradeMarket;
		this.cancelNumber = cancelNumber;
	}











	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
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
	
	public double getTotalExchangeMoney() {
		return totalExchangeMoney;
	}

	public void setTotalExchangeMoney(double totalExchangeMoney) {
		this.totalExchangeMoney = totalExchangeMoney;
	}
	
	public long getStockBalance() {
		return stockBalance;
	}






	public void setStockBalance(long stockBalance) {
		this.stockBalance = stockBalance;
	}



	public String getOrderId() {
		return orderId;
	}











	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}











	public long getTransactionOrderId() {
		return transactionOrderId;
	}






	public void setTransactionOrderId(long transactionOrderId) {
		this.transactionOrderId = transactionOrderId;
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

	public double getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(double transferFee) {
		this.transferFee = transferFee;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public int getCancelNumber() {
		return cancelNumber;
	}

	public void setCancelNumber(int cancelNumber) {
		this.cancelNumber = cancelNumber;
	}

	
}
