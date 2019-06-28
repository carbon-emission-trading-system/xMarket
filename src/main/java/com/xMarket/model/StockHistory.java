package com.xMarket.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table
public class StockHistory implements Serializable{
	@Id
	@Column
	private int stockHistory_Id;
	
	@Column
	private String stockId;
	
	@Column
	private Date date;
	@Column
	private double openPrice;
	
	@Column
	private double closePrice;
	@Column
	private double highestPrice;
	@Column
	private double lowestPrice;
	@Column
	private int volume;
	
	public int getStockHistory_Id() {
		return stockHistory_Id;
	}
	public void setStockHistory_Id(int stockHistory_Id) {
		this.stockHistory_Id = stockHistory_Id;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	public double getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	

	
	
	
}
