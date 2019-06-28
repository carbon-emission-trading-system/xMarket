package com.xMarket.VO;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.beans.BeanUtils;

import com.xMarket.model.Index;

public class IndexVO implements Serializable {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8850157996324595939L;


	

	private String indexId;
	
	
	private String indexName;
	
	private String indexPinyin;
	
	private double baseCapitalization;

	private double baseIndex;
	
	private int tradeMarket;//交易市场   market
	
	
	private double todayOpenIndex;
	
	private double increase;
	
	private double upsAndDowns;//涨跌
	
	private double tradeAmount; //成交额
	
	private int volume;//成交量
	
	private double lowestIndex;//最低价
	
	private double lastIndex; //最新成交价
	
	private double highestIndex; //最高价
	
	private double yesterdayCloseIndex;
	
	private int increaseStocks;
	
	private int decreaseStocks;
	
	private int flatStocks;
	
	private double marketCapitalization;
	
	public IndexVO() {
	}

	public IndexVO(Index index) {
		// TODO Auto-generated constructor stub
		BeanUtils.copyProperties(index, this);
	}

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

	public double getTodayOpenIndex() {
		return todayOpenIndex;
	}

	public void setTodayOpenIndex(double todayOpenIndex) {
		this.todayOpenIndex = todayOpenIndex;
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

	public double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getLowestIndex() {
		return lowestIndex;
	}

	public void setLowestIndex(double lowestIndex) {
		this.lowestIndex = lowestIndex;
	}

	public double getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(double lastIndex) {
		this.lastIndex = lastIndex;
	}

	public double getHighestIndex() {
		return highestIndex;
	}

	public void setHighestIndex(double highestIndex) {
		this.highestIndex = highestIndex;
	}

	public double getYesterdayCloseIndex() {
		return yesterdayCloseIndex;
	}

	public void setYesterdayCloseIndex(double yesterdayCloseIndex) {
		this.yesterdayCloseIndex = yesterdayCloseIndex;
	}

	public int getIncreaseStocks() {
		return increaseStocks;
	}

	public void setIncreaseStocks(int increaseStocks) {
		this.increaseStocks = increaseStocks;
	}

	public int getDecreaseStocks() {
		return decreaseStocks;
	}

	public void setDecreaseStocks(int decreaseStocks) {
		this.decreaseStocks = decreaseStocks;
	}

	public int getFlatStocks() {
		return flatStocks;
	}

	public void setFlatStocks(int flatStocks) {
		this.flatStocks = flatStocks;
	}

	public double getMarketCapitalization() {
		return marketCapitalization;
	}

	public void setMarketCapitalization(double marketCapitalization) {
		this.marketCapitalization = marketCapitalization;
	}
	
	
	
}
