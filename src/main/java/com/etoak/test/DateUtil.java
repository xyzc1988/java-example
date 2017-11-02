package com.etoak.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date parseDate(String date, String format)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new Exception();
		}
	}

	/**
	 * 
	 * 功能说明：得到当前系统日期date类型
	 * 
	 * @return
	 * 
	 */
	public static Date getDateTodaytime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 
	 * 功能说明：得到制定格式类型的当前系统日期date类型
	 * 
	 * @return
	 * 
	 */
	public static Date getDateTodaytime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * 功能说明：得到当前系统日期date类型
	 * 
	 * @return
	 * 
	 */
	public static Date getDateToday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 功能说明：将Date类型日期转换字符串
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String parseDateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 
	 * 功能说明：将String类型日期转换字符串yyyyMMdd HHmm
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static String parseStrToStr(String datestr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
		try {
			Date d = df.parse(datestr);
			String str = sdf.format(d);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see 取得当前时间（格式为：yyyyMMddHHmmss）
	 * @return String
	 */
	public static String getDateTime() {
		return getTimeFormat("yyyyMMddHHmmss");
	}

	/**
	 * @see 按指定格式取得当前时间()
	 * @return String
	 */
	public static String getTimeFormat(String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(new Date());
		return sDate;
	}

	/**
	 * 得到当前时间的字符格式
	 */
	public static String getCurrentTime() {
		return format(new Date());
	}

	/**
	 * " 格式化成系统常用日期格式：yyyyMMddHHmmss
	 */
	public static String format(Date date) {
		return format(date, "yyyyMMddHHmmss");
	}

	/**
	 * 格式化日期
	 */
	public static String format(Date date, String formatStr) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sf = new SimpleDateFormat(formatStr);
		return sf.format(date);
	}

	/**
	 * 检查时间 的合法性
	 * 
	 * @param currentTime
	 * @param format
	 * @return
	 */
	public static boolean checkCurrentTime(String currentTime, String format) {
		try {
			Date date = DateUtil
					.parseDate(currentTime.replace("-", ""), format);
			Calendar calendar = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(new Date());
			calendar.setTime(date);
			if (calendar.before(calendar2)) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	public static String formatDateString(String str) {
		if (str.length() != 14) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(str.substring(0, 4)).append("-")
				.append(str.substring(4, 6)).append("-")
				.append(str.substring(6, 8)).append(" ")
				.append(str.substring(8, 10)).append(":")
				.append(str.substring(10, 12)).append(":")
				.append(str.substring(12, 14));
		return buffer.toString();
	}

	/**
	 * 功能说明：将String类型yyyyMMddhhmms转换yyyy-MM-dd hh:mm:ss格式
	 * 
	 * @param date
	 * @return
	 */
	public static String parseStringToStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date time = sdf.parse(dateStr);
			return sdf2.format(time).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 比较开始时间和结束时间， 如果开始时间在结束时间之前则返回ture；否则返回false
	 * 
	 * @param currentTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static boolean CompareTime(String currentTime, String afterTime,
			String format) {
		try {
			Date beginTime = DateUtil.parseDate(currentTime.replace("-", ""),
					format);
			Date endTime = DateUtil.parseDate(afterTime.replace("-", ""),
					format);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beginTime);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(endTime);

			if (calendar.before(calendar2)) {
				return true;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 将14位字符格式转换为时间格式 12位 yyyy-MM-dd HH:ss
	 * 
	 * @param str
	 * @return
	 */
	public static String formatDateString1(String str) {
		if (str.length() != 14) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(str.substring(0, 4)).append("-")
				.append(str.substring(4, 6)).append("-")
				.append(str.substring(6, 8)).append(" ")
				.append(str.substring(8, 10)).append(":")
				.append(str.substring(10, 12));
		return buffer.toString();
	}
}
