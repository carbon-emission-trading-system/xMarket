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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="user_fund")
public class UserFund implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserFund(){}
	

	

	
	public UserFund(User user) {
		super();
		this.user = user;
		this.id=user.getUserId();
		this.balance = 1000000;
	}





	@Id
	@Column
	private int id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column
	private double balance;
	
	@Column
	private double frozenAmount;

	

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}





	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}




	

}
