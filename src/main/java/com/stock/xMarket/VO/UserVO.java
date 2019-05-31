package com.stock.xMarket.VO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class UserVO implements Serializable{
	
	private static final long serialVersionUID = 7521391360002308184L;

	
	private String userName;
	
	private String loginPassword;
	
	private String transactionPassword;
	
	private String email;

	private int userId;
	
	private String dbflag;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getTransactionPassword() {
		return transactionPassword;
	}

	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDbflag() {
		return dbflag;
	}

	public void setDbflag(String dbflag) {
		this.dbflag = dbflag;
	}
	

	

	
}
