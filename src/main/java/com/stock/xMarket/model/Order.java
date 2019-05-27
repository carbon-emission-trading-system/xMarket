package com.stock.xMarket.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="oorder")
public class Order implements Serializable{
	

	public Order(){}

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private long OrderId;

	
	
	@ManyToOne
	@JoinColumn(name="stock_id")
	private Stock stock;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@Column(name="local_time")
	private Time localTime;
	
	@Column(name="type")
	private int type;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="order_amount")
	private int orderAmount;

	@Column(name="exchange_amount")
	private int exchangeAmount;
	
	@Column(name="order_price",columnDefinition = "decimal")
	private double orderPrice;


	
	@Column(name="exchange_average_price" ,columnDefinition = "decimal")
	private double exchangeAveragePrice;

	@Column(name="cancel_number")
	private int cancel_number;
	
}