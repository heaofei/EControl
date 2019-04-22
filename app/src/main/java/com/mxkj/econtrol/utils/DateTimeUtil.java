package com.mxkj.econtrol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * @author cjl
 * @version V1.0
 * @Title: DateTimeUtil.java
 * @Package com.flygift.framework.util
 * @Description: 日期时间格式转换工具
 * @date 2014年12月26日 下午11:16:34
 */

@SuppressLint("SimpleDateFormat")
public class DateTimeUtil {

    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat TIME_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat YMDH_FORMAT_DATE = new SimpleDateFormat("yyyyMMddHH");

    public static final SimpleDateFormat MINUTE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat SENCOND_FORMAT_DATE = new SimpleDateFormat("yyyyMMddHHmmss");


    /**
     * @return String
     * @Title: getMinute
     * @Description: 获取到分的时间
     */
    public static String getMinute() {
        return MINUTE_FORMAT_DATE.format(new Date(System.currentTimeMillis()));
    }

    /**
     * @return String
     * @Title: getMinute
     * @Description: 获取到分的时间
     */
    public static String getMinute(long time) {
        return MINUTE_FORMAT_DATE.format(new Date(time));
    }


    /**
     * @return String
     * @Title: getSecond
     * @Description: 获取到秒的时间
     */
    public static synchronized String getSecond() {
        String timeStr = SENCOND_FORMAT_DATE.format(new Date(System.currentTimeMillis()));
        return timeStr;
    }

    /**
     * @param timeInMillis
     * @return String
     * @author：liaoxy
     * @Description: 将毫秒转为标准时间
     */
    public static synchronized String getTime(long timeInMillis) {
        if (timeInMillis == 0)
            return "";
        return MINUTE_FORMAT_DATE.format(new Date(timeInMillis));
    }


    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static synchronized String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is
     *
     * @param timeInMillis
     * @return
     */
    public static synchronized String getDate(long timeInMillis) {
        if (timeInMillis == 0)
            return "";
        return getTime(timeInMillis, DATE_FORMAT_DATE);
    }

    /**
     * @return int
     * @Title: getCurrentYear
     * @Description: 获取当前的年份
     */
    public static synchronized int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR); // 获取当前年份
    }

    /**
     * @return String
     * @Title: getYearMonthDayHour
     * @Description: 获取年月日时
     */
    public static synchronized String getYearMonthDayHour() {
        return getTime(System.currentTimeMillis(), YMDH_FORMAT_DATE);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static synchronized long getCurrentTimeLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is
     *
     * @return
     */
    public static synchronized String getCurrentTimeString() {
        return getDate(getCurrentTimeLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static synchronized String getCurrentTimeString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeLong(), dateFormat);
    }

    /**
     * 格式化日期 从左至右分别为-年-月-日 时:分:秒.毫秒 {0:yyyy-MM-dd HH:mm:ss.fff}:使用24小时制格式化日期
     * {0:yyyy-MM-dd hh:mm:ss.fff}:使用12小时制格式化日期
     *
     * @param datetime
     * @param format
     * @return
     */
    public static synchronized String formatDate(String datetime, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            Date date = formater.parse(datetime);
            return formater.format(date);
        } catch (ParseException e) {
            return datetime;
        }
    }

    // *************************************************************************

    /**
     * 【】(获取格式化的时和分)
     *
     * @param timeInMillis
     * @return
     */
    // *************************************************************************
    public static synchronized String getFormatHourMinute(long timeInMillis) {
        try {
            String formatTime = "";
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date date = new Date(timeInMillis);
            String time = df.format(date);
            int hour = date.getHours();
            if (hour < 11) {
                formatTime = "上午" + time;
            } else if (hour > 13) {
                formatTime = "下午" + time;
            } else {
                formatTime = "中午" + time;
            }
            return formatTime;
        } catch (Exception e) {
            // TODO: handle exception
            return timeInMillis + "";
        }
    }

    // *************************************************************************

    /**
     * 【】(获取倒计时的时间)
     *
     * @param second
     * @return
     */
    // *************************************************************************
    public static synchronized String getCountDownTime(long second) {
        int hour = (int) (second / 3600);
        int minute = (int) ((second - 3600 * hour) / 60);
        int sec = (int) (second % 60);
        String shour = hour >= 10 ? hour + "" : "0" + hour;
        String sminute = minute >= 10 ? minute + "" : "0" + minute;
        String sSec = sec >= 10 ? sec + "" : "0" + sec;
        String time = shour + ":" + sminute + ":" + sSec;
        return time;
    }

    /**
     * 解析时间，获取秒数
     */
    public static synchronized long getSecond(String time) {
        long seconds = 0;
        Date before;
        Date now = new Date();
        try {
            before = DATE_FORMAT_DATE.parse(time);
            seconds = now.getTime() - before.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return seconds;
    }

    /**
     * @param date
     * @return boolean
     * @author：liaoxy
     * @Description: 是否是今天
     */
    public static boolean isToday(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

        /**
     * @param time
     * @return boolean
     * @author：liaoxy
     * @Description: 是否是今天
     */
    public static boolean isToday(long time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(time));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * @Desc: 是否是今年
     * @author:liangshan
     */
    public static boolean isCurrentYear(long time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(time));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * @Desc: 是否超过今天
     * @author:liangshan
     */
    public static boolean isBeforeToDay(Date date) {
        Date today = new Date(System.currentTimeMillis());
        return date.before(today) ;

    }



    /**
     * 获取两次时间差，如几分钟前，几小时前
     */
    public static String getTime(long time1, long time2) {
        String timeStr = "";
        long timeSpan = Math.abs(time2 - time1);
        if (timeSpan > 172800000) {
            if (isCurrentYear(time2)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
                timeStr = simpleDateFormat.format(new Date(time2));
            } else {
                timeStr = getMinute(time2);
            }
        } else if (timeSpan > 86400000) {
            timeStr = "昨天";
        } else if (timeSpan > 43200000) {
            timeStr = "12小时前";
        } else if (timeSpan > 28800000) {
            timeStr = "8小时前";
        } else if (timeSpan > 14400000) {
            timeStr = "4小时前";
        } else if (timeSpan > 7200000) {
            timeStr = "2小时前";
        } else if (timeSpan > 3600000) {
            timeStr = "1小时前";
        } else if (timeSpan > 1800000) {
            timeStr = "半小时前";
        } else if (timeSpan > 900000) {
            timeStr = "15分钟前";
        } else if (timeSpan > 600000) {
            timeStr = "10分钟前";
        } else if (timeSpan > 300000) {
            timeStr = "5分钟前";
        } else if (timeSpan > 180000) {
            timeStr = "2分钟前";
        } else if (timeSpan > 120000) {
            timeStr = "1分钟前";
        } else {
            timeStr = "刚刚";
        }

        return timeStr;
    }


    /**
     * 获取两次时间差，如几分钟前，几小时前
     */
    public static String getTimeHM(long time1, long time2) {
        String timeStr = "";
        long timeSpan = Math.abs(time2 - time1);
        if (timeSpan > 172800000) {
            if (isCurrentYear(time2)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                timeStr = simpleDateFormat.format(new Date(time2));
            } else {
                timeStr = getMinute(time2);
            }
        } else if (timeSpan > 86400000) {
            timeStr = "昨天";
        } else if (timeSpan > 43200000) {
            timeStr = "12小时前";
        } else if (timeSpan > 28800000) {
            timeStr = "8小时前";
        } else if (timeSpan > 14400000) {
            timeStr = "4小时前";
        } else if (timeSpan > 7200000) {
            timeStr = "2小时前";
        } else if (timeSpan > 3600000) {
            timeStr = "1小时前";
        } else if (timeSpan > 1800000) {
            timeStr = "30分钟前";
        } else if (timeSpan > 900000) {
            timeStr = "15分钟前";
        } else if (timeSpan > 600000) {
            timeStr = "10分钟前";
        } else if (timeSpan > 300000) {
            timeStr = "5分钟前";
        } else if (timeSpan > 180000) {
            timeStr = "2分钟前";
        } else if (timeSpan > 120000) {
            timeStr = "1分钟前";
        } else {
            timeStr = "刚刚";
        }

        return timeStr;
    }

    /**
     * 获取两次时间差，如几分钟前，几小时前
     */
    public static String getTimeHM(long time2) {
        String timeStr = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        timeStr = simpleDateFormat.format(new Date(time2));

        return timeStr;
    }




    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate( long startTime, long endTime) {

        if (startTime==0 || endTime==0) {
            return false;
        }
        Date nowDate = new Date(System.currentTimeMillis());
        Date starDate = new Date(startTime);
        Date endDate = new Date(endTime);
        return  isEffectiveDate(nowDate,starDate,endDate);

    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

}
