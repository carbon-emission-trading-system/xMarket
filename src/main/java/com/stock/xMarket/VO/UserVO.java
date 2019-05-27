package com.stock.xMarket.VO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class UserVO implements Serializable{
	
	private static final long serialVersionUID = 7521391360002308184L;

	
	private String username;
	
	private String loginPassword;
	
	private String transactionPassword;
	
	private String email;

	private long id;
	
	
	private String dbflag;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDbflag() {
		return dbflag;
	}

	public void setDbflag(String dbflag) {
		this.dbflag = dbflag;
	}
	

	

	
}
