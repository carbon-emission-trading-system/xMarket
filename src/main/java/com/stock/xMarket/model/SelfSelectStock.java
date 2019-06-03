package com.stock.xMarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="self_select_stock")
public class SelfSelectStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="self_select_stock_id")
	private int selfSelectStockId; //自选股id
	
	@Column(name="user_id")
	private int userId; //用户id
	
	@Column(name="stock_id")
	private int stockId; //股票id
	
	public int getSelfSelectStockId() {
		return selfSelectStockId;
	}
	public void setSelfSelectStockId(int selfSelectStockId) {
		this.selfSelectStockId = selfSelectStockId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	
}
