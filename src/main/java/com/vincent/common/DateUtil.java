package com.vincent.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.vincent.common.log.Logger;

public class DateUtil {
	
	private static final Logger log = new Logger(DateUtil.class);
	
	public static final int SEQ_DATE_LEN = 8;
	public static final String CLIENT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String CLIENT_MONTH_FORMAT = "yyyy-MM";
	public static final String CLIENT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CLIENT_DATE_MINUTES_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String CLIENT_MINUTES_IN_DAY_FORMAT = "HH:mm";
	private static final DateFormat seqDateFormat = new SimpleDateFormat("yyyyMMdd");
	private static final Pattern timePattern = Pattern.compile("[1-9]{1}\\d*[dhms]{0,1}");

	public static String getSequenceDate(Date date){
		return seqDateFormat.format(date);
	}
	
	public static Long getSeconds(String timeString) {
		try{
			if(StringUtil.isNullOrEmpty(timeString, false) == false){
				long time = 0;
				for(String timeSplit : timeString.split("\\s")){
					if(timeSplit.isEmpty()) continue;
					
					timeSplit = timeSplit.toLowerCase();
					if(timePattern.matcher(timeSplit).matches()){
						if(timeSplit.endsWith("s")){
							time += 1L * NumberUtil.getInteger(timeSplit.replaceAll("\\D", ""));
						}else if(timeSplit.endsWith("m")){
							time += 60L * NumberUtil.getInteger(timeSplit.replaceAll("\\D", ""));
						}else if(timeSplit.endsWith("h")){
							time += 60L * 60L * NumberUtil.getInteger(timeSplit.replaceAll("\\D", ""));
						}else if(timeSplit.endsWith("d")){
							time += 24L * 60L * 60L * NumberUtil.getInteger(timeSplit.replaceAll("\\D", ""));
						}else{
							time += 1L * NumberUtil.getInteger(timeSplit);
						}
					}
				}
				
				if(time >= 0){
					return time;
				}
			}
		}catch(RuntimeException re){
		}
		
		return null;
	}
	
	public static Date add(Calendar calendar, Integer field, Integer quantity){
		if(calendar == null || field==null || quantity==null){
			return null;
		}
		calendar.add(field, quantity);
		return calendar.getTime();
		
	}
	
	public static Date maxMilliSecond(Date date){
		Calendar c = Calendar.getInstance();
		if(date != null){
			c.setTime(date);
		}
	    c.set(Calendar.MILLISECOND, 999);
	    return c.getTime();
	}
	
	public static Date minMilliSecond(Date date){
		Calendar c = Calendar.getInstance();
		if(date != null){
			c.setTime(date);
		}
	    c.set(Calendar.MILLISECOND, 000);
	    return c.getTime();
	}
	
	public static String clientDate(Date date, TimeZone tz){
		if(date == null) return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_FORMAT);
		if(tz!=null){
			sdf.setTimeZone(tz);
		}
		
		return sdf.format(date);
	}
	
	public static Date getDate(String date){
		if(StringUtil.isNullOrEmpty(date, true)) 
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_FORMAT);
		
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	public static Date getDateWithMinutes(String date){
		if(StringUtil.isNullOrEmpty(date, true)) 
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_MINUTES_FORMAT);
		
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 加减几天
	 * @param dateString
	 * @return
	 */
	public static Date getDateByDays(String dateString, int days){
		if(dateString == null) return null;
		try {
			
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_FORMAT);
		Date date = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
		
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 加减月份
	 * @param dateString
	 * @return
	 */
	public static Date getDateByMonths(String dateString, int months){
		if(dateString == null) return null;
		try {
			
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_MONTH_FORMAT);
		Date date = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
		
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date getStartDate(String dateString){
		if(dateString == null) return null;
		try {
			
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_FORMAT);
		Date date = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
		
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	public static Date getEndDate(String dateString){
		if(dateString == null) return null;
		try {
			
		SimpleDateFormat sdf = new SimpleDateFormat(CLIENT_DATE_FORMAT);
		Date date = sdf.parse(dateString);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
		
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date getNowStartDate(){
				
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date max(){
		return new Date( Long.MAX_VALUE );
	}
	/**yyyy-mm-dd*/
	public static String getDate(Date date){
		if(date == null) return "";
		return new SimpleDateFormat(CLIENT_DATE_FORMAT).format(date);
	}
	
	public static String getDateWithMinutes(Date date){
		if(date == null) return "";
		return new SimpleDateFormat(CLIENT_DATE_MINUTES_FORMAT).format(date);
	}
	
	
	public static String getHourAndMinutes(Date date){
		if(date == null) return "";
		return new SimpleDateFormat(CLIENT_MINUTES_IN_DAY_FORMAT).format(date);
	}
	
	public static String getDateInMonth(Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM").format(date);
	}
	
	public static String getYear(Date date){
		if(date == null) return "";
		return new SimpleDateFormat("yyyy").format(date);
	}
	
	public static String getMonth(Date date) {
		if (date == null) return "";
		return new SimpleDateFormat("MM").format(date);
	}
	
	public static String getDateTime(Date date){
		if(date == null) return "";
		return new SimpleDateFormat(CLIENT_DATE_TIME_FORMAT).format(date);
	}
	
	public static String getDateTime(Date date, String format){
		if(date == null) return "";
		return new SimpleDateFormat(format).format(date);
	}

	public static boolean isIntervalGreatorThan(Date date1, Date date2,
			long timeInSecond) {
		if(date1==null || date2==null){
			return false;
		}
		return Math.abs(date1.getTime()-date2.getTime())>(timeInSecond*1000);
	}

	public static Date getDateInDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		
		if(seqDateFormat.format(date1).equals(seqDateFormat.format(date2))){
			return true;
		}
		
		return false;
	}
	
	/**
	 *  Note: The calendar value is changed
	 * @param calendar
	 * @return
	 */
	public static Date minTimeInDay(Calendar calendar){
		return minTimeInDay(calendar, false);
	}
	
	public static Date minTimeInDay(Calendar calendar, boolean keepOriginal){
		long time = -1;
		if(keepOriginal){
			time = calendar.getTimeInMillis();
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date ret = calendar.getTime();
		if(keepOriginal){
			calendar.setTimeInMillis(time);
		}
		
		return ret;
	}
	
	/**
	 * Note: The calendar value is changed
	 * @param calendar
	 * @return
	 */
	public static Date maxTimeInDay(Calendar calendar){
		return maxTimeInDay(calendar, false);
	}
	
	public static Date maxTimeInDay(Calendar calendar, boolean keepOriginal){
		long time = -1;
		if(keepOriginal){
			time = calendar.getTimeInMillis();
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		Date ret = calendar.getTime();
		if(keepOriginal){
			calendar.setTimeInMillis(time);
		}
		
		return ret;
	}
	
	public static String formatSecondsToHour(long secondsCount){
		long seconds = secondsCount % 60;  
	    secondsCount -= seconds; 
	    long minutesCount = secondsCount / 60;  
	    long minutes = minutesCount % 60;  
	    minutesCount -= minutes;  
	    long hoursCount = minutesCount / 60;  
	    return String.format("%02d:%02d:%02d", hoursCount,minutes,seconds); 
	}
	
	public static Date now(){
		return new Date();
	}
	
	public static long getUtcTime(Date date){
		Calendar calendar = Calendar.getInstance();  
		return date.getTime()-( calendar.get(Calendar.ZONE_OFFSET)  
                + calendar.get(Calendar.DST_OFFSET));
	}

	public static Date maxTimeInMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	public static Date minTimeInMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date parse(String date, String format) {
		Date ret = null;
		if(StringUtil.isNullOrEmpty(date) == false){
			try {
				ret = new SimpleDateFormat(format).parse(date);
			} catch (Exception e) {
				log.error("Date String["+date+"] not match format : " + format);
			}
		}
		return ret;
	}
	
	/**
	 * 判断当前是否在规定的某个日期几天之前 
	 * holidays 休息的日期
	 * notHolidays 不休息的日期
	 */
	public static Date getDateDeadLine(String tillDate, int daysLimit, List<String> holidays, List<String> notHolidays){
		Date deadDate = DateUtil.getDate(tillDate);
		while(daysLimit > 0){
			//如果是节假日忽略，如果是调休日减一，最后周六周末减一
			deadDate = DateUtil.getDateByDays(DateUtil.getDate(deadDate), -1);
			String deadDateString = DateUtil.getDate(deadDate);
			if(holidays.indexOf(deadDateString)!=-1){
				continue;
			}
			if(notHolidays.indexOf(deadDateString)!=-1){
				daysLimit--;
				continue;
			}
			if(DateUtil.isWeekend(deadDate)){
				continue;
			}
			daysLimit--;
		}
		
		return deadDate;
	}
	/**
	 * 获取当前两个日期之间含有多少个周六和周日
	 */
	public static int getWeekendSize(Date startDate, Date endDate){
		int gap = DateUtil.getGapCount(startDate, endDate);
		int weekendDays = 0;
		for(int i =0 ; i< gap ;i ++){
			if(isWeekend(DateUtil.getDateByDays(DateUtil.getDate(startDate), i))){
				weekendDays++;
			}
		}
		return weekendDays;
	}
	
	/**
	 * 周六，周日均为节假日
	 */
	public static boolean isWeekend(Date date){
		//先判断是否有法定节假日
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(date);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取两个日期之间的间隔天数
	 * @return
	 */
	public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();  
        fromCalendar.setTime(startDate);  
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        fromCalendar.set(Calendar.MINUTE, 0);  
        fromCalendar.set(Calendar.SECOND, 0);  
        fromCalendar.set(Calendar.MILLISECOND, 0);  
  
        Calendar toCalendar = Calendar.getInstance();  
        toCalendar.setTime(endDate);  
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
        toCalendar.set(Calendar.MINUTE, 0);  
        toCalendar.set(Calendar.SECOND, 0);  
        toCalendar.set(Calendar.MILLISECOND, 0);  
  
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}
}
