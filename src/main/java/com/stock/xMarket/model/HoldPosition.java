package com.stock.xMarket.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="hold_position")
public class HoldPosition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hold_position_id")
	private int holdPositionId;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="stock_id")
	private Stock stock;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="cost_price")
	private double costPrice;
	
	@Column(name="position_number")
	private int positionNumber;
	
	@Column(name="available_number")
	private int availableNumber;
	
	@Transient
	private int frozenNumber;
	
	@Column(name="opening_time")
	private Date openingTime;

	

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public int getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(int positionNumber) {
		this.positionNumber = positionNumber;
	}

	public int getAvailableNumber() {
		return availableNumber;
	}

	public void setAvailableNumber(int availableNumber) {
		this.availableNumber = availableNumber;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	@Override
	public String toString() {
		return "HoldPosition [hold_position_id=" + holdPositionId + ", stock=" + stock + ", user=" + user
				+ ", costPrice=" + costPrice + ", positionNumber=" + positionNumber + ", availableNumber="
				+ availableNumber + ", frozenNumber=" + frozenNumber + ", openingTime=" + openingTime + "]";
	}

	public int getFrozenNumber() {
		return frozenNumber;
	}

	public void setFrozenNumber(int frozenNumber) {
		this.frozenNumber = frozenNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
