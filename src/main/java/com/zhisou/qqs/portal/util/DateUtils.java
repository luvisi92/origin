package com.zhisou.qqs.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 
 * @author ZR
 * 
 */
public abstract class DateUtils {

	private static Calendar calChinese = Calendar.getInstance(Locale.CHINESE);
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			DateStyle.YYYY_MM_DD.getValue());
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			DateStyle.HH_MM_SS.getValue());

	/**
	 * 获得当前年份
	 * 
	 * @return
	 */
	public static int getYear() {
		return calendar().get(Calendar.YEAR);
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getBetweenDays(String start, String end) {
		if (start == null || start.length() == 0)
			return 0;
		if (end == null || end.length() == 0)
			return 0;
		Date tmp1 = null;
		Date tmp2 = null;
		long day = 0;
		try {
			tmp1 = dateFormat.parse(start);
			tmp2 = dateFormat.parse(end);
			day = (tmp2.getTime() - tmp1.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			//
		}

		return day;
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern
	 *            格式化模式，详见{@link SimpleDateFormat}构造器
	 *            <code>SimpleDateFormat(String pattern)</code>
	 * @return
	 */
	public static String formatDatetime(Date date, DateStyle pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
				.clone();
		customFormat.applyPattern(pattern.getValue());
		return customFormat.format(date);
	}
	
	public static String formatDatetime(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		return dateFormat.format(date);
	}

	public static Date transformDatetime(Date date, DateStyle pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
				.clone();
		customFormat.applyPattern(pattern.getValue());
		return parseDatetime(customFormat.format(date));
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}


	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Calendar calendar() {
		// Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		return Calendar.getInstance();
	}

	/**
	 * 获得当前时间的毫秒数
	 * <p>
	 * 详见{@link System#currentTimeMillis()}
	 * 
	 * @return
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 获得当前Chinese月份
	 * 
	 * @return
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH);
	}

	/**
	 * 获得月份中的第几天
	 * 
	 * @return
	 */
	public static int dayOfMonth() {
		return calendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是星期的第几天
	 * 
	 * @return
	 */
	public static int dayOfWeek() {
		return calendar().get(Calendar.DAY_OF_WEEK);
	}

	public static int dayOfWeek(Date date) {
	    Calendar c = calendar();
		c.setTime(date);
		int w = c.get(Calendar.DAY_OF_WEEK);
		if (w < 0)
			w = 0;
		return w;
	}
	
    public static int dayOfChineseWeek(Date date) {
        calChinese.setTime(date);
        calChinese.setFirstDayOfWeek(Calendar.MONDAY);
        int w = calChinese.get(Calendar.DAY_OF_WEEK) -1;
        if (w  == 0)
            w = 7;
        return w;
    }

	/**
	 * 今天是年中的第几天
	 * 
	 * @return
	 */
	public static int dayOfYear() {
		return calendar().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断原日期是否在目标日期之前
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isBefore(Date src, Date dst) {
		return src.before(dst);
	}

	/**
	 * 判断原日期是否在目标日期之后
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isAfter(Date src, Date dst) {
		return src.after(dst);
	}

	/**
	 * 判断两日期是否相同
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqual(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	/**
	 * 判断某个日期是否在某个日期范围
	 * 
	 * @param beginDate
	 *            日期范围开始
	 * @param endDate
	 *            日期范围结束
	 * @param src
	 *            需要判断的日期
	 * @return
	 */
	public static boolean between(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 获得当前月的最后一天
	 * <p>
	 * HH:mm:ss为0，毫秒为999
	 * 
	 * @return
	 */
	public static Date lastDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置零
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
		cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
		return cal.getTime();
	}

	/**
	 * 获取给定日期的最后一天
	 * 
	 * @param date
	 * @return Date
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth(Date date) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}

	/**
	 * 获得当前月的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 * 
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}

	private static Date weekDay(int week) {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime();
	}

	/**
	 * 获得周五日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date friday() {
		return weekDay(Calendar.FRIDAY);
	}

	/**
	 * 获得周六日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date saturday() {
		return weekDay(Calendar.SATURDAY);
	}

	/**
	 * 获得周日日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date sunday() {
		return weekDay(Calendar.SUNDAY);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) {
		if (datetime == null || datetime.length() == 0) {
			return null;
		}
		try {
			return datetimeFormat.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) {
		if (date == null || date.length() == 0) {
			return null;
		}
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	
	public static Date parseDate(long lDate){
	    return new Date(lDate*1000l);
	}


	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 时间格式 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String time) {
		if (time == null || time.length() == 0) {
			return null;
		}
		try {
			return timeFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 根据自定义pattern将字符串日期转换成java.util.Date类型
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDatetime(String datetime, DateStyle pattern) {
		if (datetime == null || datetime.length() == 0) {
			return null;
		}
		SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
		format.applyPattern(pattern.getValue());
		Date date = null;
		try {
			date = format.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	public static enum DateStyle {
        DD("dd"),D("d"),
		YYYY("yyyy"), MM_DD("MM-dd"), YYYY_MM("yyyy-MM"), YYYY_MM_DD(
				"yyyy-MM-dd"), MM_DD_HH_MM("MM-dd HH:mm"), MM_DD_HH_MM_SS(
				"MM-dd HH:mm:ss"), YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"), YYYY_MM_DD_HH_MM_SS(
				"yyyy-MM-dd HH:mm:ss"), YYYY_MM_DD_HH_MM_00(
				"yyyy-MM-dd HH:mm:00"), YYYY_MM_DD_HH_MM_59(
				"yyyy-MM-dd HH:mm:59"), YYYY_MM_DD_00_00_00(
				"yyyy-MM-dd 00:00:00"), YYYY_MM_DD_23_59_59(
				"yyyy-MM-dd 23:59:59"), YYYY_MM_DD_HH_00_00(
				"yyyy-MM-dd HH:00:00"), YYYY_MM_DD_HH_59_59(
				"yyyy-MM-dd HH:59:59"), YYYY_MM_DD_DOT_SEPARATORT("yyyy.MM.dd"),

		MM_DD_EN("MM/dd"), YYYY_MM_EN("yyyy/MM"), YYYY_MM_DD_EN("yyyy/MM/dd"), MM_DD_HH_MM_EN(
				"MM/dd HH:mm"), MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"), YYYY_MM_DD_HH_MM_EN(
				"yyyy/MM/dd HH:mm"), YYYY_MM_DD_HH_MM_SS_EN(
				"yyyy/MM/dd HH:mm:ss"),

		MM_DD_CN("MM月dd日"), YYYY_MM_CN("yyyy年MM月"), YYYY_MM_DD_CN("yyyy年MM月dd日"), MM_DD_HH_MM_CN(
				"MM月dd日 HH:mm"), MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"), YYYY_MM_DD_HH_MM_CN(
				"yyyy年MM月dd日 HH:mm"), YYYY_MM_DD_HH_MM_SS_CN(
				"yyyy年MM月dd日 HH:mm:ss"),

		HH_MM("HH:mm"), HH_MM_SS("HH:mm:ss"), YYYYMMDDHHMMSSSSS(
				"yyyyMMddHHmmssSSS"), YYYYMMDD("yyyyMMdd"),YYYYMMDDHHMMSS(
		                "yyyyMMddHHmmss");

		private String value;

		DateStyle(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 获取当前时间 前days 时间 {method description}
	 * 
	 * @param days
	 * @return
	 */
	public static Date getCurrentDateBeforeDay(int days) {
	    Calendar c = calendar();
		c.setTime(new Date());
		if (days != 0) {
			c.add(Calendar.DATE, (-days));
			return c.getTime();
		} else {
			return c.getTime();
		}
	}

	public static Date getDateAfterDays(Date currentDate, int days) {
	    Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (days != 0) {
			c.add(Calendar.DATE, days);
			return c.getTime();
		} else {
			return c.getTime();
		}
	}

	/**
	 * 上周周一
	 * 
	 * @return
	 */
	public static Date getPreviousMonday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周二
	 * 
	 * @return
	 */
	public static Date getPreviousTuesDay() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周三
	 * 
	 * @return
	 */
	public static Date getPreviousWednesday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周四
	 * 
	 * @return
	 */
	public static Date getPreviousThursDAY() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周五
	 * 
	 * @return
	 */
	public static Date getPreviousFriday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周六
	 * 
	 * @return
	 */
	public static Date getPreviousSaturday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 上周周日
	 * 
	 * @return
	 */
	public static Date getPreviousSunday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		Date date = c.getTime();
		return date;
	}

	/**
	 * 本周第一天
	 * 
	 * @return
	 */
	public static Date getWeekOfFirstDay() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 本周最后一天
	 * 
	 * @return
	 */
	public static Date getWeekOfLastDay() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 获得上周所有日期
	 * 
	 * @return
	 */
	public static List<Date> getLastWeekDay() {
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(getPreviousMonday());
		dateList.add(getPreviousTuesDay());
		dateList.add(getPreviousWednesday());
		dateList.add(getPreviousThursDAY());
		dateList.add(getPreviousFriday());
		dateList.add(getPreviousSaturday());
		dateList.add(getPreviousSunday());
		return dateList;
	}

	/**
	 * 获得上月第一天的日期
	 * 
	 * @return
	 */
	public static Date getPreviousMonthFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		Date date = lastDate.getTime();
		return date;
	}

	/**
	 * 获得上月最后一天的日期
	 * 
	 * @return
	 */
	public static Date getPreviousMonthEnd() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		Date date = lastDate.getTime();
		return date;
	}



	public static boolean validateDate(String strDate, DateStyle pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) dateFormat.clone();
		customFormat.applyPattern(pattern.getValue());
		boolean bool = false;
		try {
			// Format f = new SimpleDateFormat(format);
			Date d = (Date) customFormat.parseObject(strDate);
			String tmp = customFormat.format(d);
			if (strDate.equals(tmp)) {
				bool = true;
			} else {
				bool = false;
			}
		} catch (ParseException e) {
			bool = false;
		}
		return bool;
	}



	/**
	 * 返回两个日期之间的所有日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return List<Date>
	 */
	public static List<Date> getBetweenDates(Date startDate, Date endDate) {
		List<Date> allDayList = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		while (startDate.before(endDate) || startDate.getTime() == endDate.getTime()) {
			allDayList.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
		}
		return allDayList;
	}
	
	   /**
     * 返回两个日期之间的所有日期
     * 
     * @param startDate
     * @param endDate
     * @return List<Date>
     */
    public static List<Date> getBetweenDates(long startDate, long endDate) {
        List<Date> allDayList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startDate*1000l);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startTime = calendar.getTime();
        Date endTime = new Date(endDate*1000l);
        while (startTime.before(endTime) || startTime.compareTo(endTime) == 0) {
            allDayList.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            startTime = calendar.getTime();
        }
        return allDayList;
    }
    
	public static void  main(String[] args){
	    List<Date> dates = getBetweenDates(1420041600,1451491200);
        
	    System.out.println("-----" + formatDatetime(firstDayOfMonth()));
	    System.out.println(formatDatetime(getLastDayOfMonth(new Date())));
	    System.out.println(formatDatetime(lastDayOfMonth()));
	    
	}
	
	/**
	 * 计算两个时间相隔的月份数
	 * @author JiangTao
	 * @since 2016-6-17
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 计算两个时间相隔的月份数
	 */
	public static int getBetweenMonth(String startDate, String endDate){
	    if (startDate == null || startDate.length() == 0)
            return 0;
        if (endDate == null || endDate.length() == 0)
            return 0;
        
        int betweenMonth = 0;
	    try
        {
    	    SimpleDateFormat sdf=new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
            
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            
            cal1.setTime(sdf.parse(startDate));
            
            cal2.setTime(sdf.parse(endDate));
            int year =cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
            
            
            //开始日期若小于结束日期
            if(year<0){
                year =- year;
                betweenMonth = year*12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
            }
           
            betweenMonth = year*12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
	    return betweenMonth;
	}
	
	/**
	 * 取得 
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
    public static int getBetweenDays(Date endDate, Date beginDate)
    {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(beginDate);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        
        return (int)((endCal.getTimeInMillis()/1000/60/60/24) - (startCal.getTimeInMillis()/1000/60/60/24));
    }
    
    
    /**
     * 返回指定时间段内符合匹配条件的每月天数列表
     * @author JiangTao
     * @since 2016-6-27
     * @param sdate
     * @param edate
     * @param matchDays 每月天数如: {1,2,3,4,5,6,7,14,15}
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenDatesByMatchDays(String sdate, String edate, List<String> matchDays) {
        SimpleDateFormat sdf=new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
        List<String> allDayList = new ArrayList<String>();
        
        try
        {
            Date sd = sdf.parse(sdate);
            Date ed = sdf.parse(edate);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sd.getTime());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date startTime = calendar.getTime();
            Date endTime = new Date(ed.getTime());
            String day = null;
            
            while (startTime.before(endTime) || startTime.compareTo(endTime) == 0) {
                day = formatDatetime(startTime, DateStyle.D);
                if (matchDays.contains(day)) {
                    Date dt = calendar.getTime();
                    String dstr = formatDatetime(dt, DateStyle.YYYY_MM_DD);
                    allDayList.add(dstr);
                }
                
                calendar.add(Calendar.DATE, 1);
                
                startTime = calendar.getTime();
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        return allDayList;
    }
    
    
    /**
     * 返回指定时间段内符合匹配条件的每周天数列表
     * @author JiangTao
     * @since 2016-6-27
     * @param sdate
     * @param edate
     * @param matchDays 每周天数如: {1,2,3,4,5,6,7}
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenWDatesByMatchDays(String sdate, String edate, List<String> matchDays) {
        SimpleDateFormat sdf=new SimpleDateFormat(DateStyle.YYYY_MM_DD.getValue());
        List<String> allDayList = new ArrayList<String>();
        
        try
        {
            Date sd = sdf.parse(sdate);
            Date ed = sdf.parse(edate);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sd.getTime());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date startTime = calendar.getTime();
            Date endTime = new Date(ed.getTime());
            String day = null;
            
            while (startTime.before(endTime) || startTime.compareTo(endTime) == 0) {
                Calendar cal = Calendar.getInstance();      
                cal.setTime(startTime);  
                day = (cal.get(Calendar.DAY_OF_WEEK) - 1) + "";
                if(day.equals("0")){
                    day = "7";
                }
                if (matchDays.contains(day)) {
                    Date dt = calendar.getTime();
                    String dstr = formatDatetime(dt, DateStyle.YYYY_MM_DD);
                    allDayList.add(dstr);
                }
                
                calendar.add(Calendar.DATE, 1);
                
                startTime = calendar.getTime();
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        
        return allDayList;
    }
    
    /**
	 * 把日期转换成字符串型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern){
		if(date == null){
			return "";
		}
		if(pattern == null){
			pattern = "yyyy-MM-dd";
		}
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			dateString = sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}
}