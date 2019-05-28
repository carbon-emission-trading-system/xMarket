package com.stock.xMarket.VO;

import java.sql.Date;
import java.sql.Time;

public interface TransactionOrderProjection {
	
	Date getDate();//成交日期
	
	Time getTime();//成交时间
	
	int getStockId();
	
	String getStockName();
	
	Boolean getsellId();//操作（买入卖出 true是买入）
	
	double getTradePrice();//成交价
	
	int getExchangeAmount();//成交数量
	
	double getExchangeFee();//成交金额
	
	int getStockBalance();//股票余额
	
	String getSellOrderId();//委托单id--合同编号
	
	String getTransactionOrderId();//历史成交单id--成交编号
	
	double getServiceTax();//手续费
	
	double getStampTax();//印花税
	
	double getIncidentalCharge();//其他杂费
	
	double getActualAmount();//发生金额
	
	String getMarket();//交易市场
	
	//int getRevokeAmount();//撤单数量---数据库无

}
