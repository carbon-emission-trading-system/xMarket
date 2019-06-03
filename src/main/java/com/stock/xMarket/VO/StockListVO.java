package com.stock.xMarket.VO;

public class StockListVO {
    private int stockId;//股票代码
    private String stockName;//股票名称
    private double lastTradePrice;//最新成交价
    private double increase;//今日涨幅
    private double highestPrice;//最高价
    private double lowestPrice;//最低价
    private double todayOpenPrice;//今日开盘价
    private double yesterdayOpenPrice;//昨日开盘价
    private double turnover;//成交额
    private double marketValue;//市值
    private double priceEarningsRatio;//市盈率
    private double priceToBookRatio;//市净率

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

    public double getTodayOpenPrice() {
        return todayOpenPrice;
    }

    public void setTodayOpenPrice(double todayOpenPrice) {
        this.todayOpenPrice = todayOpenPrice;
    }

    public double getYesterdayOpenPrice() {
        return yesterdayOpenPrice;
    }

    public void setYesterdayOpenPrice(double yesterdayOpenPrice) {
        this.yesterdayOpenPrice = yesterdayOpenPrice;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getPriceEarningsRatio() {
        return priceEarningsRatio;
    }

    public void setPriceEarningsRatio(double priceEarningsRatio) {
        this.priceEarningsRatio = priceEarningsRatio;
    }

    public double getPriceToBookRatio() {
        return priceToBookRatio;
    }

    public void setPriceToBookRatio(double priceToBookRatio) {
        this.priceToBookRatio = priceToBookRatio;
    }
}
