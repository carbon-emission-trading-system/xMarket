package com.xMarket.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="stock")
public class Stock implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7367673609684216060L;

	public Stock(){}
	
	@Id
	@Column(name="stock_id")
	private String stockId;
	
	@Column(name="stock_name")
	private String stockName;
	
	@Column
	private String stockPinyin;
	
	@Column(name="share_capital")
	private long shareCapital;
	

	@Column(name="earnings_per_share")
	private double earningsPerShare;

	@Column(name="bookValue")
	private double bookValue; 
	
	@Column(name="trade_market")
	private int tradeMarket;//交易市场   market

	
	
	
	
	
	
	public String getStockId() {
		return stockId;
	}

	public double getBookValue() {
		return bookValue;
	}

	public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
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

	public long getShareCapital() {
		return shareCapital;
	}

	public void setShareCapital(long shareCapital) {
		this.shareCapital = shareCapital;
	}

	public double getEarningsPerShare() {
		return earningsPerShare;
	}

	public void setEarningsPerShare(double earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public String getStockPinyin() {
		return stockPinyin;
	}

	public void setStockPinyin(String stockPinyin) {
		this.stockPinyin = stockPinyin;
	}

	
	

	

	
}
