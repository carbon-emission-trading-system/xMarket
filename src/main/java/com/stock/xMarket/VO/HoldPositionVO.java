package com.stock.xMarket.VO;

import java.io.Serializable;

public class HoldPositionVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int stockId;
	private String stockName;
	private double presentPrice; //现价 就是市价
	private double costPrice; //成本价---- 
	private int actualAmount; //实际数量  = 股票余额（positionNumber） 
	//备选，公式太复杂啦！
	//private double todayProfitAndLoss; //当日盈亏 = 
	private double totalProfitAndLoss; //总盈亏 = 成本价 * 股票余额 - 市值
	private double profitAndLossRatio; //盈亏比例=（ 市价 - 成本价）/成本价
	private double marketValue; //市值 = 市价*股票余额 
	private double positionRatio; //仓位占比 = 市值/总资产
	
	
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
	public double getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(double presentPrice) {
		this.presentPrice = presentPrice;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public int getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(int actualAmount) {
		this.actualAmount = actualAmount;
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
	public double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	public double getPositionRatio() {
		return positionRatio;
	}
	public void setPositionRatio(double positionRatio) {
		this.positionRatio = positionRatio;
	}
	
	
	
}
