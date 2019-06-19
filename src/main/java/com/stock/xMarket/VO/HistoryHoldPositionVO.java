package com.stock.xMarket.VO;

import java.util.Date;

public class HistoryHoldPositionVO {

	private String stockId;
	private String stockName;
	private Date buildPositionDate;
	private Date clearPositionDate;
	private int stockHoldDay;
	private double totalProfitAndLoss;
	//private double totalPurchaseAmount;
	private double profitAndLossRatio;
	
	public HistoryHoldPositionVO(String stockId, String stockName, Date buildPositionDate, Date clearPositionDate,
			int stockHoldDay, double totalProfitAndLoss, double profitAndLossRatio) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.buildPositionDate = buildPositionDate;
		this.clearPositionDate = clearPositionDate;
		this.stockHoldDay = stockHoldDay;
		this.totalProfitAndLoss = totalProfitAndLoss;
		this.profitAndLossRatio = profitAndLossRatio;
	}
	
	public HistoryHoldPositionVO() {
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

	public double getProfitAndLossRatio() {
		return profitAndLossRatio;
	}

	public void setProfitAndLossRatio(double profitAndLossRatio) {
		this.profitAndLossRatio = profitAndLossRatio;
	}
	
	
	
}
