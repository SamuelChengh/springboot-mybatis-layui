package com.ch.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * 获取指定格式的String类型日期
     *
     * @param pattern 日期格式
     */
    public static String getCurrentDateStr(String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date date = new Date();

        return sdf.format(date);
    }

    /**
     * 将String类型的日期转化成Date类型
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     */
    public static Date stringToDate(String dateStr, String pattern) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 将Date类型的日期转化成String类型
     *
     * @param date    日期
     * @param pattern 日期格式
     */
    public static String dateToString(Date date, String pattern) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }

    /**
     * 将10位yyyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd转换为8位yyyyMMdd日期
     *
     * @param dateStr 10位日期字符串
     */
    public static String dateTo8(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return "";
        }

        return dateStr.substring(0, 4) + dateStr.substring(5, 7) + dateStr.substring(8, 10);
    }

    /**
     * 将8位yyyyMMdd转换为10位yyyy/MM/dd,yyyy-MM-dd,yyyy:MM:dd日期
     *
     * @param dateStr 8位日期字符串
     * @param sign    分隔符
     */
    public static String dateTo10(String dateStr, String sign) {

        if (StringUtils.isBlank(dateStr)) {
            return "";
        }

        return dateStr.substring(0, 4) + sign + dateStr.substring(4, 6) + sign + dateStr.substring(6, 8);
    }

    /**
     * 获取当前日期是星期几, 0:星期天、1:星期一、···、6:星期六
     */
    public static String getWeekStr() {
        String s = "";

        int week = getWeek();
        switch (week) {
            case 0:
                s = "星期天";
                break;
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
            default:
                break;
        }

        return s;
    }

    public static int getWeek() {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        week--;

        return week;
    }

    /**
     * 获取某一个日期的星期
     *
     * @param dateStr 日期字符串
     */
    public static String getWeek(String dateStr) throws ParseException {

        if (StringUtils.isBlank(dateStr)) {
            return "";
        }

        final String[] dayNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = sdf.parse(dateStr);
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayNames[dayOfWeek - 1];
    }

    /**
     * 获取当前月第一天
     */
    public static String getCurrentMonthFirstDay() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

        return format.format(c.getTime());
    }

    /**
     * 获取某一个日期所在月的第一天
     *
     * @param dateStr 日期字符串
     */
    public static String getMonthFirstDay(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

        return sdf.format(c.getTime());
    }

    /**
     * 获取当前月最后一天
     */
    public static String getCurrentMonthLastDay() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        return format.format(c.getTime());
    }

    /**
     * 获取某一个日期所在月的最后一天
     *
     * @param dateStr 日期字符串
     */
    public static String getMonthLastDay(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        return sdf.format(c.getTime());
    }

    /**
     * 获取最近7天日期
     */
    public static List<String> getSevenDays() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> list = new ArrayList<String>();
        Date date = new Date();
        Date endTime = date;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -6);
        Date startTime = c.getTime();

        while (startTime.before(endTime)) {
            String s = sdf.format(startTime);
            list.add(s);

            c.setTime(startTime);
            c.add(Calendar.DAY_OF_YEAR, 1);
            startTime = c.getTime();
        }
        list.add(sdf.format(endTime));

        return list;
    }

    /**
     * 判断结束时间是否大于开始时间
     * <p>
     * 大于返回true，小于返回false
     */
    public static boolean checkPeriodTime(String periodStart, String periodEnd, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date startTime = null;
        Date endTime = null;
        try {
            startTime = sdf.parse(periodStart);
            endTime = sdf.parse(periodEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (endTime.getTime() > startTime.getTime()) {
            return true;
        }

        return false;
    }

    /**
     * 判断某个时间，是否包含在某时间段内
     * <p>
     * 若包含返回true; 否则返回false
     */
    public static boolean isBetweenPeriod(String target, String periodStart, String periodEnd, String pattern) {

        if (StringUtils.isBlank(periodStart) || StringUtils.isBlank(periodEnd)) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date targetTime = null;
        Date startTime = null;
        Date endTime = null;
        try {
            targetTime = sdf.parse(target);
            startTime = sdf.parse(periodStart);
            endTime = sdf.parse(periodEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (targetTime.getTime() >= startTime.getTime() && targetTime.getTime() <= endTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getBetweenDaysList(String startTime, String endTime) {

        // 返回的日期集合
        List<String> daysList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1); // 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                daysList.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return daysList;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBetweenDaysList("2020-04-21", "2020-05-21"));
    }
}