package com.stock.xMarket.util;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class UUIDUtil {

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	

		
 
		public static int getGuid(int userId) {
			
 
			int now = (int) (System.currentTimeMillis()/1000);  
			String info=userId+now+"";
			int id= Integer.parseInt(info);
					
			return id;  
		}


	
	
}
