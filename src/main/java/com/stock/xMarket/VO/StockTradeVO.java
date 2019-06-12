package com.stock.xMarket.VO;

public class StockTradeVO {
    private int stockId;//股票代码
    private String stockName;//股票名称
    private double balance;//用户资金
    private int tradeMarket;//交易所市场
    private double OrderPrice;//最新成交价
    private double availableNumber;//可用余额
    private double openPrice;//开盘价

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(int tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public double getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        OrderPrice = orderPrice;
    }

    public double getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(double availableNumber) {
        this.availableNumber = availableNumber;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }
}
