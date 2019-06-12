package com.stock.xMarket.VO;

import java.io.Serializable;

public class StockListVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int stockId;
	private String stockName;
	private double lastTradePrice; //最新成交价
	private double increase; //涨幅
	private double highestPrice; //最高价
	private double lowestPrice; //最低价
	private double openPrice; //今日开盘价
	private double yesterdayOpenPrice; //昨日开盘价
	private double yesterdayClosePrice; //昨日收盘价
	private double tradeAmount; //总成交额
	private double  totalMarketCapitalization; //总市值
	private double peRatio ; //市盈率
	private double pbRatio; //市净率
	private int tradeMarket; //0--深A，1--沪A
	
	
	
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
	public double getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	public double getIncrease() {
		return increase;
	}
	public void setIncrease(double increase) {
		this.increase = increase;
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
	public double getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
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
	public StockListVO(int stockId, String stockName, double lastTradePrice, double increase, double highestPrice,
			double lowestPrice, double openPrice, double yesterdayOpenPrice, double yesterdayClosePrice,
			double tradeAmount, double totalMarketCapitalization, double peRatio, double pbRatio, int tradeMarket) {
	
		this.stockId = stockId;
		this.stockName = stockName;
		this.lastTradePrice = lastTradePrice;
		this.increase = increase;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.openPrice = openPrice;
		this.yesterdayOpenPrice = yesterdayOpenPrice;
		this.yesterdayClosePrice = yesterdayClosePrice;
		this.tradeAmount = tradeAmount;
		this.totalMarketCapitalization = totalMarketCapitalization;
		this.peRatio = peRatio;
		this.pbRatio = pbRatio;
		this.tradeMarket = tradeMarket;
	}
	public StockListVO() {
	}
	
}
