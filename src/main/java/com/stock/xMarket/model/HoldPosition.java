package com.stock.xMarket.model;

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
public class HoldPosition {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int hold_position_id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="stock_id")
	private Stock stock;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column
	private double costPrice;
	
	@Column
	private int positionNumber;
	
	@Column
	private int availableNumber;
	
	@Transient
	private int frozenNumber;
	
	@Column
	private Date openingTime;

	public int getHold_position_id() {
		return hold_position_id;
	}

	public void setHold_position_id(int hold_position_id) {
		this.hold_position_id = hold_position_id;
	}

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

	
	
	
}
