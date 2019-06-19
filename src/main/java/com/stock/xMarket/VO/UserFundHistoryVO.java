package com.stock.xMarket.VO;

import java.sql.Date;

public class UserFundHistoryVO {
    private int UserFundHistory_Id;

    private int userId;

    private Date date;

    private double totalMarketValue;

    public UserFundHistoryVO() {
    }

    public UserFundHistoryVO(int userFundHistory_Id, int userId, Date date, double totalMarketValue) {
        UserFundHistory_Id = userFundHistory_Id;
        this.userId = userId;
        this.date = date;
        this.totalMarketValue = totalMarketValue;
    }

    public int getUserFundHistory_Id() {
        return UserFundHistory_Id;
    }

    public void setUserFundHistory_Id(int userFundHistory_Id) {
        UserFundHistory_Id = userFundHistory_Id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }
}
