package com.stock.xMarket.VO;

import java.io.Serializable;

public class RealTimeVO implements Serializable{
	
	
	
	
	
	
	
	public RealTimeVO(int stockID,  float commissionProportion, float openPrice, float highestPrice,
			float lowestPrice, float latestPrice, float upsAndDowns, float increase, float outMarket, float inMarket,
			float conversionHand, float totalMarketCapitalization, float staticPERatio, float dailyLimit,
			float downLimitBoard, float cityNet) {
		super();
		this.stockID = stockID;
		this.commissionProportion = commissionProportion;
		this.openPrice = openPrice;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.latestPrice = latestPrice;
		this.upsAndDowns = upsAndDowns;
		this.increase = increase;
		this.outMarket = outMarket;
		this.inMarket = inMarket;
		this.conversionHand = conversionHand;
		this.totalMarketCapitalization = totalMarketCapitalization;
		this.staticPERatio = staticPERatio;
		this.dailyLimit = dailyLimit;
		this.downLimitBoard = downLimitBoard;
		this.cityNet = cityNet;
	}
	
	private int stockID;
	private float commissionProportion;
	private float openPrice;
	private float highestPrice;
	private float lowestPrice;
	private float latestPrice;
	private float upsAndDowns;
	private float increase;
	private float outMarket;
	private float inMarket;
	private float conversionHand;
	private float totalMarketCapitalization;
	private float staticPERatio;
	private float dailyLimit;
	private float downLimitBoard;
	private float cityNet;
	public int getStockID() {
		return stockID;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public float getCommissionProportion() {
		return commissionProportion;
	}
	public void setCommissionProportion(float commissionProportion) {
		this.commissionProportion = commissionProportion;
	}
	public float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}
	public float getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(float highestPrice) {
		this.highestPrice = highestPrice;
	}
	public float getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public float getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(float latestPrice) {
		this.latestPrice = latestPrice;
	}
	public float getUpsAndDowns() {
		return upsAndDowns;
	}
	public void setUpsAndDowns(float upsAndDowns) {
		this.upsAndDowns = upsAndDowns;
	}
	public float getIncrease() {
		return increase;
	}
	public void setIncrease(float increase) {
		this.increase = increase;
	}
	public float getOutMarket() {
		return outMarket;
	}
	public void setOutMarket(float outMarket) {
		this.outMarket = outMarket;
	}
	public float getInMarket() {
		return inMarket;
	}
	public void setInMarket(float inMarket) {
		this.inMarket = inMarket;
	}
	public float getConversionHand() {
		return conversionHand;
	}
	public void setConversionHand(float conversionHand) {
		this.conversionHand = conversionHand;
	}
	public float getTotalMarketCapitalization() {
		return totalMarketCapitalization;
	}
	public void setTotalMarketCapitalization(float totalMarketCapitalization) {
		this.totalMarketCapitalization = totalMarketCapitalization;
	}
	public float getStaticPERatio() {
		return staticPERatio;
	}
	public void setStaticPERatio(float staticPERatio) {
		this.staticPERatio = staticPERatio;
	}
	public float getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(float dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public float getDownLimitBoard() {
		return downLimitBoard;
	}
	public void setDownLimitBoard(float downLimitBoard) {
		this.downLimitBoard = downLimitBoard;
	}
	public float getCityNet() {
		return cityNet;
	}
	public void setCityNet(float cityNet) {
		this.cityNet = cityNet;
	}
	
	
}
