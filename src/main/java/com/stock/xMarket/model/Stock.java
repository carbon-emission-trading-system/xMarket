package com.stock.xMarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="stock")
public class Stock implements Serializable{
	

	public Stock(){}
	
	
	
	@Id
	@Column(name="stock_id")
	private int stockId;

	@Column(name="stock_name")
	private String stockName;

	@Column(name="share_capital")
	private String shareCapital;
	

	@Column(name="earnings_per_share")
	private String earningsPerShare;

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

	public String getShareCapital() {
		return shareCapital;
	}

	public void setShareCapital(String shareCapital) {
		this.shareCapital = shareCapital;
	}

	public String getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(String earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}
}
