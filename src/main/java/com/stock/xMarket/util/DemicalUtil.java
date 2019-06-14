package com.stock.xMarket.util;

import java.math.BigDecimal;

public class DemicalUtil {

	  /*	 * 获得的是double类型	 * 保留两位小数        */	
    public static double keepTwoDecimal(double num){		
    	if(num==Double.POSITIVE_INFINITY||num==Double.NEGATIVE_INFINITY||Double.isNaN(num))
    		return num;
    	BigDecimal bg = new BigDecimal(num);		
    	double num1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		
    	return num1;
    }
	
	  /*	 * 获得的是double类型	 * 保留三位小数        */	
    public static double keepThreeDecimal(double num){		
    	if(num==Double.POSITIVE_INFINITY||num==Double.NEGATIVE_INFINITY||Double.isNaN(num))
    		return num;
    	BigDecimal bg = new BigDecimal(num);		
    	double num1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();		
    	return num1;
    }
    
}
