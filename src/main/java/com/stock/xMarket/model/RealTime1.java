package com.stock.xMarket.model;

import java.io.Serializable;
import java.util.List;

public class RealTime1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String stockId;
	private double lastTradePrice; // 最新成交价
	private double openPrice; // 今日开盘价
	private double closePrice;
	private double yesterdayClosePrice; // 昨日收盘价
	private double highestPrice; // 最高价
	private double lowestPrice; // 最低价
	private int volume; // 成交量
	private double tradeAmount; // 成交额

	private List<Gear> buyOneToFive;// 买一买五
	private List<Gear> sellOneToFive;// 卖一卖五

	private int inMarket;
	private int outMarket;

	public RealTime1(String stockId, double lastTradePrice, double openPrice, double closePrice,
			double yesterdayClosePrice, double highestPrice, double lowestPrice, int volume, double tradeAmount,
			List<Gear> buyOneToFive, List<Gear> sellOneToFive, int inMarket, int outMarket) {
		super();
		this.stockId = stockId;
		this.lastTradePrice = lastTradePrice;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.yesterdayClosePrice = yesterdayClosePrice;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.volume = volume;
		this.tradeAmount = tradeAmount;
		this.buyOneToFive = buyOneToFive;
		this.sellOneToFive = sellOneToFive;
		this.inMarket = inMarket;
		this.outMarket = outMarket;
	}

	public int getInMarket() {
		return inMarket;
	}

	public void setInMarket(int inMarket) {
		this.inMarket = inMarket;
	}

	public int getOutMarket() {
		return outMarket;
	}

	public void setOutMarket(int outMarket) {
		this.outMarket = outMarket;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public RealTime1() {
	}

	public double getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}

	public void setYesterdayClosePrice(double yesterdayClosePrice) {
		this.yesterdayClosePrice = yesterdayClosePrice;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public double getLastTradePrice() {
		return lastTradePrice;
	}

	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
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

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public List<Gear> getBuyOneToFive() {
		return buyOneToFive;
	}

	public void setBuyOneToFive(List<Gear> buyOneToFive) {
		this.buyOneToFive = buyOneToFive;
	}

	public List<Gear> getSellOneToFive() {
		return sellOneToFive;
	}

	public void setSellOneToFive(List<Gear> sellOneToFive) {
		this.sellOneToFive = sellOneToFive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
