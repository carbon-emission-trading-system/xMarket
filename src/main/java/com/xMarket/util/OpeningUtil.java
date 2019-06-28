package com.xMarket.util;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OpeningUtil {

//	public static boolean isOpening(Time time) {
//		boolean s = false;
//		try {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(time);
//			int hour = cal.get(Calendar.HOUR_OF_DAY);
//			int minutes = cal.get(Calendar.MINUTE);
//			if (hour >= 0 && hour < 10) {
//				if (hour >= 9) {
//					if (minutes >= 30)// 9:30-10:00
//						s = true;
//					else
//						// 9:00-9:30
//						s = false;
//				} else {// 0:00-9:00
//					s = false;
//				}
//			} else if (hour > 9 && hour < 12) {
//				if (hour > 10) {
//					if (hour >= 11) {
//						if (minutes >= 30)// 11:30-12:00
//							s = false;
//						else
//							// 11:00-11:30
//							s = true;
//					} else {// 10:00-11:00
//						s = true;
//					}
//				} else {
//					if (minutes >= 30)// 9:30-10:00
//						s = true;
//					else
//						// 9:00-9:30
//						s = false;
//				}
//			} else if (hour >= 11 && hour < 13) {
//				if (hour < 12) {
//					if (minutes >= 30)// 11:30-12:00
//						s = false;
//					else
//						// 11:00-11:30
//						s = true;
//				} else {
//					s = false;
//				}
//			} else if (hour > 12 && hour < 15) {
//				if (hour >= 13) {// 13:00-15:00
//					s = true;
//				} else {// 12:00-13:00
//					s = false;
//				}
//			} else if (hour >= 15 && hour <= 24) {// 15:00-24:00
//				s = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return s;
//		}
//		return s;
//	}

//	public static boolean isSet(Time time) {
//		boolean s = false;
//		try {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(time);
//			int hour = cal.get(Calendar.HOUR_OF_DAY);
//			int minutes = cal.get(Calendar.MINUTE);
//			if (hour >= 0 && hour < 10) {
//				if (hour == 9) {
//					if (minutes >= 25)// 9:30-10:00
//						s = false;
//					else
//						// 9:00-9:30
//						s = true;
//				} else {// 0:00-9:00
//					s = true;
//				}
//			} else if (hour == 14) {
//				if (minutes >= 57) {
//					s = true;
//				}
//			} else if (hour >= 15 && hour <= 24) {// 15:00-24:00
//				s = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return s;
//		}
//		return s;
//	}

	public static boolean isOpening(Time time) {
		boolean s = false;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minutes = cal.get(Calendar.MINUTE);
			if (hour >= 0 && hour < 10) {
				if (hour >= 9) {
					if (minutes >= 30)// 9:30-10:00
						s = true;
					else
						// 9:00-9:30
						s = false;
				} else {// 0:00-9:00
					s = false;
				}
			} else if(hour>=10&&hour<22){
				s = true;
			}
			else if (hour >= 22 && hour <= 24) {// 15:00-24:00
				s = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return s;
		}
		return s;
	}

	public static boolean isSet(Time time) {
		boolean s = false;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minutes = cal.get(Calendar.MINUTE);

			if (hour >= 0 && hour < 10) {
				if (hour == 9) {
					if (minutes >= 25)
						s = false;
					else
						s = true;
				} else {
					s = true;
				}
			} else if (hour == 21) {
				if (minutes >= 57) {
					s = true;
				}
			} else if (hour >= 22 && hour <= 24) {// 22:00-24:00
				s = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return s;
		}
		return s;
	}

}
