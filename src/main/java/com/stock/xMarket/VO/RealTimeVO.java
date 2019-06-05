package com.stock.xMarket.VO;

import java.io.Serializable;

public class RealTimeVO implements Serializable{
	

	

	public RealTimeVO() {
		// TODO Auto-generated constructor stub
	}

	
	
	private int stockId;
	private String stockName;
	private double lowestPrice;//最低价
	private double latestPrice;//最新价
	private double highestPrice; //最高价
	private double increase; //涨幅
	private double upsAndDowns;//涨跌
	//private double dailyLimit;//涨停价
	//private double downLimit;//跌停价
	private double openPrice; //今日开盘价
	private double yesterdayOpenPrice; //昨日开盘价
	private double yesterdayClosePrice;//昨日收盘价
	private double commissionProportion;//委比
	private double tradeAmount; //成交额
	private int tradeVolumn;//成交量
	private double  totalMarketCapitalization; //总市值
	private double peRatio ; //市盈率
	private double pbRatio; //市净率
	private int tradeMarket; //0--深A，1--沪A
	private double outMarket;//外盘
	private double inMarket;//内盘
	private double conversionHand;//换手

	
	

	public int getStockId() {
		return stockId;
	}




	public void setStockId(int stockId) {
		this.stockId = stockId;
	}




	public String getStockName() {
		return stockName;
	}




	public void setStockName(String stockName) {
		this.stockName = stockName;
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




	public double getHighestPrice() {
		return highestPrice;
	}




	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}




	public double getIncrease() {
		return increase;
	}




	public void setIncrease(double increase) {
		this.increase = increase;
	}




	public double getUpsAndDowns() {
		return upsAndDowns;
	}




	public void setUpsAndDowns(double upsAndDowns) {
		this.upsAndDowns = upsAndDowns;
	}




	public double getOpenPrice() {
		return openPrice;
	}




	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}




	public double getYesterdayOpenPrice() {
		return yesterdayOpenPrice;
	}




	public void setYesterdayOpenPrice(double yesterdayOpenPrice) {
		this.yesterdayOpenPrice = yesterdayOpenPrice;
	}




	public double getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}




	public void setYesterdayClosePrice(double yesterdayClosePrice) {
		this.yesterdayClosePrice = yesterdayClosePrice;
	}




	public double getCommissionProportion() {
		return commissionProportion;
	}




	public void setCommissionProportion(double commissionProportion) {
		this.commissionProportion = commissionProportion;
	}




	public double getTradeAmount() {
		return tradeAmount;
	}




	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}




	public int getTradeVolumn() {
		return tradeVolumn;
	}




	public void setTradeVolumn(int tradeVolumn) {
		this.tradeVolumn = tradeVolumn;
	}




	public double getTotalMarketCapitalization() {
		return totalMarketCapitalization;
	}




	public void setTotalMarketCapitalization(double totalMarketCapitalization) {
		this.totalMarketCapitalization = totalMarketCapitalization;
	}




	public double getPeRatio() {
		return peRatio;
	}




	public void setPeRatio(double peRatio) {
		this.peRatio = peRatio;
	}




	public double getPbRatio() {
		return pbRatio;
	}




	public void setPbRatio(double pbRatio) {
		this.pbRatio = pbRatio;
	}




	public int getTradeMarket() {
		return tradeMarket;
	}




	public void setTradeMarket(int tradeMarket) {
		this.tradeMarket = tradeMarket;
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




	public RealTimeVO(int stockId) {
		// TODO Auto-generated constructor stub
		
		this.stockId = stockId;
	}


	
	
}
