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
		public static double sellTaxCaculator(double money){
			
			return money*0.00102;
		}
		
	public static double buyTaxCaculator(double money){
			
			return money*0.00002;
		}
}
