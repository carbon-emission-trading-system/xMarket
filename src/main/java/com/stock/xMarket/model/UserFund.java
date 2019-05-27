package com.stock.xMarket.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="user_fund")
public class UserFund implements Serializable{
	
	private static final long serialVersionUID = 7521391360002308184L;

	public UserFund(){}
	






	@Id
	@Column(name="id")
	private String id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_name")
	private User user;
	
	@Column(name="money")
	private double money;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}
