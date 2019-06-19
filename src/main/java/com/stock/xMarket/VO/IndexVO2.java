package com.stock.xMarket.VO;

import java.io.Serializable;

public class IndexVO2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1597924069120242477L;

	
	private String indexId;
	
	private String indexName;
	
	private double lastIndex; //最新成交价
	
	private double yesterdayCloseIndex;
	
	

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public double getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(double lastIndex) {
		this.lastIndex = lastIndex;
	}

	public double getYesterdayCloseIndex() {
		return yesterdayCloseIndex;
	}

	public void setYesterdayCloseIndex(double yesterdayCloseIndex) {
		this.yesterdayCloseIndex = yesterdayCloseIndex;
	}
	
	

}
