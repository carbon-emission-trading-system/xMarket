package com.stock.xMarket.VO;

import java.sql.Date;

public class UserFundHistoryVO {
    private int UserFundHistory_Id;

    private int userId;

    private Date date;

    private double totalFunds;

    private double fundIncrease;

    private double SZZS;

    private double SZCZ;

    public UserFundHistoryVO() {
    }

  

    public UserFundHistoryVO(int userFundHistory_Id, int userId, Date date, double totalFunds, double fundIncrease,
			double sZZS, double sZCZ) {
		UserFundHistory_Id = userFundHistory_Id;
		this.userId = userId;
		this.date = date;
		this.totalFunds = totalFunds;
		this.fundIncrease = fundIncrease;
		SZZS = sZZS;
		SZCZ = sZCZ;
	}



	public double getFundIncrease() {
		return fundIncrease;
	}



	public void setFundIncrease(double fundIncrease) {
		this.fundIncrease = fundIncrease;
	}



	public double getSZZS() {
		return SZZS;
	}



	public void setSZZS(double sZZS) {
		SZZS = sZZS;
	}



	public double getSZCZ() {
		return SZCZ;
	}



	public void setSZCZ(double sZCZ) {
		SZCZ = sZCZ;
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
