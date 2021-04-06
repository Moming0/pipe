package com.bs.pipe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateHelper extends DateUtils{
	/**
     * 默认时间格式
     */
    public static final String DEF_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式数组
     */
    public static final String[] DATE_PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
        "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/MM", "yyyyMMddHHmmss","yyyy"};

    /**
     * 格式化输出
     * 
     * @param dt
     *            dt
     * @return 格式化输出 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date dt)
    {
        return format(dt, DEF_PATTERN);
    }

    /**
     * 格式化输出
     * 
     * @param dt
     *            dt
     * @param pattern
     *            格式
     * @return 格式化输出
     */
    public static String format(Date dt, String pattern)
    {
        SimpleDateFormat simpleDateFormat;
        if (dt == null)
        {
            return null;
        }
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(dt);
    }

    /**
     * Date 转换成 XMLGregorianCalendar
     * 
     * @param dt
     *            dt
     * @return XMLGregorianCalendar
     * @throws DatatypeConfigurationException
     *             异常
     */
    public static XMLGregorianCalendar toXmlDate(Date dt)
        throws DatatypeConfigurationException
    {
        if (dt == null)
        {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dt);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    /**
     * XMLGregorianCalendar 转换成 Date
     * 
     * @param cal
     *            cal
     * @return Date
     */
    public static Date toDate(XMLGregorianCalendar cal)
    {
        if (cal == null)
        {
            return null;
        }
        return cal.toGregorianCalendar().getTime();
    }

    /**
     * 日期转换
     * 
     * @param dateValue
     *            时间字符串
     * @return Date
     * @throws ParseException
     */
    public static Date parseDateIgnoreError(final String dateValue)
    {
        if (StringUtils.isBlank(dateValue))
        {
            return null;
        }

        try
        {
            return parseDate(dateValue, DATE_PATTERNS);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 日期转换
     * 
     * @param dateValue
     *            时间字符串
     * @return Date
     * @throws ParseException
     *             异常
     */
    public static Date parseDate(final String dateValue)
        throws ParseException
    {
        return parseDate(dateValue, DATE_PATTERNS);
    }

    /**
     * 获取两个日期之间的相差秒数
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 相差秒数
     */
    public static long differSeconds(Date beginDate, Date endDate)
    {
        long offsetTime = endDate.getTime() - beginDate.getTime();
        return Math.abs(offsetTime / 1000);
    }

    /**
     * 获取两个日期之间的相差分钟
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 相差分钟
     */
    public static long differMinutes(Date beginDate, Date endDate)
    {
        return differSeconds(beginDate, endDate) / 60;
    }

    /**
     * 获取两个日期之间的相差小时
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 相差小时
     */
    public static long differHours(Date beginDate, Date endDate)
    {
    	long hour = differMinutes(beginDate, endDate) / 60;
    	if(beginDate.compareTo(endDate) > 0) {
    		return hour * -1;
    	}
        return hour;
    }

    /**
     * 获取两个日期之间的相差天数
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 相差天数
     */
    public static long differDays(Date beginDate, Date endDate)
    {
        return differHours(beginDate, endDate) / 24;
    }

    /**
     * 获取当前时间的年月日时分秒 拼接的字符串
     * 
     * @return 拼接的字符串
     */
    public static String dateFullNumberStr()
    {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        // 获取年份
        String year = completeStr(String.valueOf(cal.get(Calendar.YEAR)));
        // 获取月份
        String month = completeStr(String.valueOf(cal.get(Calendar.MONTH) + 1));
        String day = completeStr(String.valueOf(cal.get(Calendar.DATE)));
        String hour = completeStr(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        String min = completeStr(String.valueOf(cal.get(Calendar.MINUTE)));
        String second = completeStr(String.valueOf(cal.get(Calendar.SECOND)));
        sb.append(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(min);
        sb.append(second);
        return sb.toString();
    }

    /**
     * @param str
     *            str
     * @return 拼接的字符串
     */
    private static String completeStr(String str)
    {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(str))
        {
            return "";
        }

        if (StringUtils.defaultString(str).length() == 1)
        {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 获取当前时间 格式：YYYYMMddHHmmssSSS
     * 
     * @return 时间字符串
     */
    public static String getNowDateMillisecond()
    {
        SimpleDateFormat dft = new SimpleDateFormat("YYYYMMddHHmmssSSS");
        String dm = dft.format(new Date());
        return dm;

    }

    /**
     * 获取今天剩余秒数
     * 
     * @return 剩余秒数
     */
    public static int getSeconds()
    {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR),
            curDate.get(Calendar.MONTH), curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        return (int)(tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * 获取Unix时间戳
     *
     * @return 当前Unix时间戳
     */
    public static long getNowTimeStamp()
    {
        return System.currentTimeMillis() / 1000;
    }
    
    /**
     * 根据出生日期算出年龄
     * @param birthDay	出生日期
     * @return
     * @throws Exception
     */
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) { // 出生日期晚于当前时间，无法计算
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR); // 当前年份
		int monthNow = cal.get(Calendar.MONTH); // 当前月份
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); // 当前日期
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth; // 计算整岁数
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;// 当前日期在生日之前，年龄减一
			} else {
				age--;// 当前月份在生日之前，年龄减一
			}
		}
		return age;
	}
}








