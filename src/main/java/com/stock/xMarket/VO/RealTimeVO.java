package com.stock.xMarket.VO;

import java.io.Serializable;

public class RealTimeVO implements Serializable{
	

	

	public RealTimeVO() {
		// TODO Auto-generated constructor stub
	}

	private int stockID;
	private double commissionProportion;
	private double openPrice;
	private double highestPrice;
	private double lowestPrice;
	private double latestPrice;
	private double upsAndDowns;
	private double increase;
	private double outMarket;
	private double inMarket;
	private double conversionHand;
	private double totalMarketCapitalization;
	private double staticPERatio;
	private double dailyLimit;
	private double downLimitBoard;
	private double cityNet;
	private int volume;
	private double ytdClosePrice;
	
	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getStockID() {
		return stockID;
	}
	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public double getCommissionProportion() {
		return commissionProportion;
	}
	public void setCommissionProportion(double commissionProportion) {
		this.commissionProportion = commissionProportion;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getHighestPrice() {
		return highestPrice;
	}
	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	public double getLowestPrice() {
		return lowestPrice;
	}
	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}
	public double getLatestPrice() {
		return latestPrice;
	}
	public void setLatestPrice(double latestPrice) {
		this.latestPrice = latestPrice;
	}
	public double getUpsAndDowns() {
		return upsAndDowns;
	}
	public void setUpsAndDowns(double upsAndDowns) {
		this.upsAndDowns = upsAndDowns;
	}
	public double getIncrease() {
		return increase;
	}
	public void setIncrease(double increase) {
		this.increase = increase;
	}
	public double getOutMarket() {
		return outMarket;
	}
	public void setOutMarket(double outMarket) {
		this.outMarket = outMarket;
	}
	public double getInMarket() {
		return inMarket;
	}
	public void setInMarket(double inMarket) {
		this.inMarket = inMarket;
	}
	public double getConversionHand() {
		return conversionHand;
	}
	public void setConversionHand(double conversionHand) {
		this.conversionHand = conversionHand;
	}
	public double getTotalMarketCapitalization() {
		return totalMarketCapitalization;
	}
	public void setTotalMarketCapitalization(double totalMarketCapitalization) {
		this.totalMarketCapitalization = totalMarketCapitalization;
	}
	public double getStaticPERatio() {
		return staticPERatio;
	}
	public void setStaticPERatio(double staticPERatio) {
		this.staticPERatio = staticPERatio;
	}
	public double getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public double getDownLimitBoard() {
		return downLimitBoard;
	}
	public void setDownLimitBoard(double downLimitBoard) {
		this.downLimitBoard = downLimitBoard;
	}
	public double getCityNet() {
		return cityNet;
	}
	public void setCityNet(double cityNet) {
		this.cityNet = cityNet;
	}

	public double getYtdClosePrice() {
		return ytdClosePrice;
	}

	public void setYtdClosePrice(double ytdClosePrice) {
		this.ytdClosePrice = ytdClosePrice;
	}

	public RealTimeVO(int stockID, double commissionProportion, double openPrice, double highestPrice,
			double lowestPrice, double latestPrice, double upsAndDowns, double increase, double outMarket,
			double inMarket, double conversionHand, double totalMarketCapitalization, double staticPERatio,
			double dailyLimit, double downLimitBoard, double cityNet, int volume, double ytdClosePrice) {
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
		this.volume = volume;
		this.ytdClosePrice = ytdClosePrice;
	}

	public RealTimeVO(int stockId) {
		// TODO Auto-generated constructor stub
		
		this.stockID = stockID;
	}


	
	
}
