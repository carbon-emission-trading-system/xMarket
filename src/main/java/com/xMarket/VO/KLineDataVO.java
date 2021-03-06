package com.xMarket.VO;

import java.sql.Date;

public class KLineDataVO {
    private String date;//日期
    private double openPrice;//开盘价
    private double closePrice;//收盘价
    private double highestPrice;//最高价
    private double lowestPrice;//最低价
    private double volume;//成交量

    
    
    public KLineDataVO() {
	}

	public KLineDataVO(String  date, double openPrice, double closePrice, double highestPrice, double lowestPrice,
			double volume) {
		super();
		this.date = date;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.volume = volume;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
