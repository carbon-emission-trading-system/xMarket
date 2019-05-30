package com.stock.xMarket.VO;

import java.util.Date;

public class HistoryHoldPositionVO {

	private int stockId;
	private String stockName;
	private Date buildPositionDate;
	private Date clearPositionDate;
	private int stockHoldDay;
	private double totalProfitAndLoss;
	private double totalPurchaseAmount;
	
	public HistoryHoldPositionVO(int stockId, String stockName, Date buildPositionDate, Date clearPositionDate,
			int stockHoldDay, double totalProfitAndLoss, double totalPurchaseAmount) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.buildPositionDate = buildPositionDate;
		this.clearPositionDate = clearPositionDate;
		this.stockHoldDay = stockHoldDay;
		this.totalProfitAndLoss = totalProfitAndLoss;
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
	
	public HistoryHoldPositionVO() {
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
	public Date getBuildPositionDate() {
		return buildPositionDate;
	}
	public void setBuildPositionDate(Date buildPositionDate) {
		this.buildPositionDate = buildPositionDate;
	}
	public Date getClearPositionDate() {
		return clearPositionDate;
	}
	public void setClearPositionDate(Date clearPositionDate) {
		this.clearPositionDate = clearPositionDate;
	}
	public int getStockHoldDay() {
		return stockHoldDay;
	}
	public void setStockHoldDay(int stockHoldDay) {
		this.stockHoldDay = stockHoldDay;
	}
	public double getTotalProfitAndLoss() {
		return totalProfitAndLoss;
	}
	public void setTotalProfitAndLoss(double totalProfitAndLoss) {
		this.totalProfitAndLoss = totalProfitAndLoss;
	}
	public double getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}
	public void setTotalPurchaseAmount(double totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
	
	
}
