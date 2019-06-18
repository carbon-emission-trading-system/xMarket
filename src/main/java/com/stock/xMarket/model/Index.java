package com.stock.xMarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stock_index")
public class Index {


	@Id
	private int indexId;
	
	@Column
	private String indexName;
	
	@Column
	private String indexPinyin;
	
	@Column
	private double baseCapitalization;

	@Column
	private double baseIndex;
	
	
	
	@Column
	private int tradeMarket;//交易市场   market

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexPinyin() {
		return indexPinyin;
	}

	public void setIndexPinyin(String indexPinyin) {
		this.indexPinyin = indexPinyin;
	}

	public double getBaseCapitalization() {
		return baseCapitalization;
	}

	public void setBaseCapitalization(double baseCapitalization) {
		this.baseCapitalization = baseCapitalization;
	}

	public double getBaseIndex() {
		return baseIndex;
	}

	public void setBaseIndex(double baseIndex) {
		this.baseIndex = baseIndex;
	}

	public int getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
	}
	
	
	
}
