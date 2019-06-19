package com.stock.xMarket.VO;

import java.io.Serializable;

public class IndexVO2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1597924069120242477L;

	
	private int indexId;
	
	private double lastIndex; //最新成交价
	
	private double yesterdayCloseIndex;

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
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
