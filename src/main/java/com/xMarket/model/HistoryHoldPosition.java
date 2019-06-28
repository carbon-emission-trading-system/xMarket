package com.xMarket.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="history_hold_position")
public class HistoryHoldPosition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="history_hold_position_id")
	private int historyHoldPositionId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="stock_id")
	private String stockId;//股票id
	
	@Column(name="stock_name")
	private String stockName;
	
	@Column(name="build_position_date")
	private Date buildPositionDate;//建仓时间
	
	@Column(name="clear_position_date")
	private Date clearPositionDate;//清仓时间
	
	@Column(name="stock_hold_day")
	private int stockHoldDay;//持股天数
	
	@Column(name="total_profit_and_loss")
	private double totalProfitAndLoss;//总盈亏
	
	//@Column(name="total_purchase_amount")
	//private double totalPurchaseAmount;//总买入金额
	
	@Column(name="profit_and_loss_ratio")
	private double profitAndLossRatio;//盈亏比例
	
	public int getHistoryHoldPositionId() {
		return historyHoldPositionId;
	}
	public void setHistoryHoldPositionId(int historyHoldPositionId) {
		this.historyHoldPositionId = historyHoldPositionId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
