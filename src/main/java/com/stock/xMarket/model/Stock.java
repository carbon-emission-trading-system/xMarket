package com.stock.xMarket.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="stock")
public class Stock implements Serializable{
	

	public Stock(){}
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="stock_id")
	private long stockId;
	
	@Column(name="share_capital")
	private String shareCapital;
	

	@Column(name="earnings_per_share")
	private String earningsPerShare;
	
	
	

	
}
