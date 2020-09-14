package com.alan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 日期累加，T+N
	 * 
	 * @param day
	 * @return
	 */
	public static String addDate(int day) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		// System.out.println("当前日期:" + sf.format(c.getTime()));
		c.add(Calendar.DAY_OF_MONTH, day);
		String newDate = sf.format(c.getTime());
		// System.out.println("增加后日期:" + newDate);
		return newDate;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		return sf.format(c.getTime());
	}

	/**
	 * 对比当前日期
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareCurrentDate(String d1) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String d0 = sf.format(c.getTime());

		Date date0 = sf.parse(d0);
		Date date1 = sf.parse(d1.substring(0, 10));

		int compareTo = date0.compareTo(date1);
		if (compareTo == 1) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) throws ParseException {
		compareCurrentDate("2019-10-14 00:00:00");
	}
}
