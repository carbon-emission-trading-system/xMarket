package com.stock.xMarket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table
public class UserFundHistory implements Serializable {
    @Id
    @Column
    private int UserFundHistory_Id;

    @Column
    private int userId;

    @Column
    private Date date;

    @Column
    private double totalMarketValue;

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
