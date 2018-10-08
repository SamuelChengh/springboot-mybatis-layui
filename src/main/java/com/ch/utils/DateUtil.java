package com.ch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * @功能描述：获取当前时间
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * @功能描述：获取当前时间(带时分秒)
     */
    public static String getCurrentDateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * @功能描述： 将10位yyyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd转换为8位yyyyMMdd日期
     */
    public static String dateTo8(String date) {
        if (date == null) {
            return "";
        }
        return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
    }

    /**
     * @功能描述： 将8位yyyyMMdd转换为10位yyyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd日期
     */
    public static String dateTo10(String date, String sign) {
        if (date == null){
            return "";
        }
        return date.substring(0, 4) + sign + date.substring(4, 6) + sign + date.substring(6, 8);
    }

    /**
     * @功能描述： 获取当前日期是星期几,0为星期天、1为星期一···6为星期六。
     */
    public static int getWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        week--;
        return week;
    }

    public static String getWeekStr() {
        String s = "";
        int week = getWeek();
        switch (week) {
            case 1:
                s = "星期一";
                break;
            case 2:
                s = "星期二";
                break;
            case 3:
                s = "星期三";
                break;
            case 4:
                s = "星期四";
                break;
            case 5:
                s = "星期五";
                break;
            case 6:
                s = "星期六";
                break;
            case 7:
                s = "星期天";
                break;
            default:
                break;
        }
        return s;
    }


    /**
     * @功能描述： 获取某一个日期的星期
     */
    public static String getWeek(String dateStr) throws ParseException {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = sdf.parse(dateStr);
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayNames[dayOfWeek - 1];
    }

    /**
     * @功能描述： 获取一个月的最后一天
     * @date: 8位yyyyMMdd日期
     */
    public static String getLastDayOfMonth(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            day = 31;
        } else if (month == 2) {
            if (((year % 4) == 0) && ((year % 100) != 0)) {
                day = 29;
            } else {
                day = 28;
            }
        } else {
            day = 30;
        }

        String lastDay = "";
        if (month < 10) {
            lastDay = year + "-0" + month + "-" + day;
        } else {
            lastDay = year + "-" + month + "-" + day;
        }

        return lastDay;
    }
}