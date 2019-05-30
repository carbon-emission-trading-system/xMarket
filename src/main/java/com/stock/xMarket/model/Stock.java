package com.stock.xMarket.model;

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
	

	public Stock(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="stock_id")
	private int stockId;
	
	@Column(name="stock_name")
	private String stockName;
	
	@Column(name="share_capital")
	private String shareCapital;
	

	@Column(name="earnings_per_share")
	private String earningsPerShare;
	
	/*@OneToMany(mappedBy = "stock")
	@JsonBackReference
	private List<Order> orders;
*/
	public long getStockId() {
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

	/*public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
*/
	

	
}
