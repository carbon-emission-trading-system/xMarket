package com.stock.xMarket.VO;

public class UserFundVO {
    private double totalFunds;
    private double holdPosProAndLos;
    private double balance;
    private double totalMarketValue;
    private double todayProAndLos;
    private double frozenAmount;
    private double amountBalance;

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public double getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(double frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
    
    public double getAmountBalance() {
    	return amountBalance;
    }
    
    public void setAmountBalance(double amountBalance) {
    	this.amountBalance=amountBalance;
    }
}
