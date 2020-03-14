package com.kaituo.comparison.back.common.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    public static String DATE_MONTH = "MM-dd";
    public static String T_FORMAT_LONG = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_HOURS = "yyyy-MM-dd-HH";
    public final static String DATE_TIME_SIMPLE = "yyyyMMdd";
    public final static String DATE_ONLY_TIME = "HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }


    public static String formatTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }


    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }


    public static Date getDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.parse(date);
    }

    public static Date getDateShort(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }


    public static Date getDateFull(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
    
    public static Date parse(String date,String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public static Date getYesterday() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(today);

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);

        Date yesterday = dateFormat.parse(dateFormat.format(calendar.getTime()));

        return yesterday;
    }

    public static String formatMins(Double mins){
        if (mins==null){
            return "--";
        }
        int min=mins.intValue();
        int m=min%60;
        int h=min/60;
        String format=m+"分钟";
        if (h>0){
            format = h+"小时"+format;
        }
        return format;
    }

    /**
     * 获取一个日期的几天
     *
     * @param date 格式 yyyy-MM-dd
     * @param days 正数为几天后，负数为几天前
     * @return
     * @throws ParseException
     */
    public static Date plusDays(Date date, int days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);

        return sdf.parse(sdf.format(calendar.getTime()));

    }

    public static Date minusDays(Date date, int days){
        return new DateTime(date).minusDays(days).toDate();
    }


    //字符串转时间
    public static Time stringForTime(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date parse = dateFormat.parse(str);
        return new Time(parse.getTime());
    }

    /**
     * 计算两个日期的时间差 xx天xx小时xx分
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static String getDateDiff(Date endDate, Date startDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = Math.abs(endDate.getTime() - startDate.getTime());
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        String string = "";
        if (day > 0) {
            string += day + "天";
        }
        if (hour > 0) {
            string += hour + "小时";
        }
        if (min > 0) {
            string += min + "分钟";
        }
        return string;
    }
    /**
     * 在日期上增加天数
     * 
     * @param date
     *            日期
     * @param n
     *            要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }
    
    public static Date addMin(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }
    
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }
    
    public static Date getPeriodTime(Date perioddate,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, perioddate.getHours());
        calendar.set(Calendar.MINUTE, perioddate.getMinutes());
        calendar.set(Calendar.SECOND, perioddate.getSeconds());
        Date zero = calendar.getTime();
        return zero;
    }
    public static String FORMAT_UTC ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * UTC时间转换成北京时间
     * @param UTCStr
     * @param format
     * @throws ParseException
     */
    public static Date UTCToCST(String UTCStr){
    	try {
    		Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_UTC);
            date = sdf.parse(UTCStr);
//            System.out.println("UTC时间: " + date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
            return calendar.getTime();
            //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
//            System.out.println("北京时间: " + calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }
}
