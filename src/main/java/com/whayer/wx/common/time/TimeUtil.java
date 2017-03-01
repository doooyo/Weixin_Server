package com.whayer.wx.common.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理类
 * @author duyu
 * @since  28-02-17
 */
public final class TimeUtil {
	private static SimpleDateFormat formation = new SimpleDateFormat("yyyy-MM-dd");

	public static String formatDay(Date date) {
		return formation.format(date);
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getBeforeDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 计算日期
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String beforeDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return beforeDay;
	}

	/**
	 * 计算指定日期的后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getAfterDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 计算日期
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		String afterDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return afterDay;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getLastDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 计算日期
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		return c;
	}

	/**
	 * 获取0点时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getMorning(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat formation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return formation.format(calendar.getTime());
	}

	/**
	 * 获取24点时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getNight(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat formation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);

		return formation.format(calendar.getTime());
	}

	/**
	 * 指定日期和今天的相差天数
	 * 
	 * @param time
	 * @return
	 */
	public static int getDiffDays(String time) {
		if (time == null || time.equals("")) {
			return 0;
		}

		try {
			Date beginDate = formation.parse(time);
			long beginUnixTime = beginDate.getTime();
			long now = System.currentTimeMillis();
			long dif = (now - beginUnixTime) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(dif));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getFirstMonthDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return formation.format(calendar.getTime());
	}

	/**
	 * 获取当月最后一天的24时
	 * 
	 * @return
	 */
	public static String getLastMonthDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return getNight(calendar.getTime());
	}

	public static Calendar getLastDay(Date date, Integer last) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - last);

		return calendar;
	}
}
