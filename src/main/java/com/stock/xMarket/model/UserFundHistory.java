package com.stock.xMarket.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table
public class UserFundHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int UserFundHistory_Id;

    @Column
    private int userId;

    @Column
    private Date date;

    @Column
    private double totalFunds;

    @Column
    private double fundIncrease;

    @Column
    private double SZZS;

    @Column
    private double SZCZ;


    public double getFundIncrease() {
        return fundIncrease;
    }

    public void setFundIncrease(double fundIncrease) {
        this.fundIncrease = fundIncrease;
    }

    public double getSZZS() {
        return SZZS;
    }

    public void setSZZS(double SZZS) {
        this.SZZS = SZZS;
    }

    public double getSZCZ() {
        return SZCZ;
    }

    public void setSZCZ(double SZCZ) {
        this.SZCZ = SZCZ;
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

    public double getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(double totalFunds) {
        this.totalFunds = totalFunds;
    }
}
