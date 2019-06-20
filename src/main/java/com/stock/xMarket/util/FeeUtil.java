package com.stock.xMarket.util;

public class FeeUtil {

	//用于计算手续费的函数
	public static double serviceFeeCaculator(double money){
		if(money>20000){
			return money*0.00025;
		}
		return 5;
	}
	
	
	//用于计算手续费的函数
		public static double stampTaxCaculator(double money){
			
			return money*0.001;
		}
		
	public static double transferFeeCaculator(double money){
			
			return money*0.00002;
		}
}
