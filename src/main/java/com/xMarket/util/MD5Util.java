package com.xMarket.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	private static String dbSalt="market";
	
	public static String md5(String str){
		return DigestUtils.md5Hex(str);
	}
	
	public static String inputToDb(String str){
		return md5(str + dbSalt);
	}
	
	
	
	
}
