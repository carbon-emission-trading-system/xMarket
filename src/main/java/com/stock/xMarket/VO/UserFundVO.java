package com.stock.xMarket.VO;

public class UserFundVO {
    private double totalFunds;
    private double holdPosProAndLos;
    private double availableFunds;
    private double totalMarketValue;
    private double todayProAndLos;
    private double freezFunds;

    public double getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(double totalFunds) {
        this.totalFunds = totalFunds;
    }

    public double getHoldPosProAndLos() {
        return holdPosProAndLos;
    }

    public void setHoldPosProAndLos(double holdPosProAndLos) {
        this.holdPosProAndLos = holdPosProAndLos;
    }

    public double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(double availableFunds) {
        this.availableFunds = availableFunds;
    }

    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public double getTodayProAndLos() {
        return todayProAndLos;
    }

    public void setTodayProAndLos(double todayProAndLos) {
        this.todayProAndLos = todayProAndLos;
    }

    public double getFreezFunds() {
        return freezFunds;
    }

    public void setFreezFunds(double freezFunds) {
        this.freezFunds = freezFunds;
    }
}
