package com.vivecal.calculator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 描述：
 * 创建日期:2021/7/2
 * 创建人：ChenKun
 */
public class DateUtil {

    public static Date parseDate(String dateString, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static long parseTimeMills(String dateString, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return dateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            return new Date().getTime();
        }
    }

    /**
     * 毫秒数转换成天时分秒
     * @param milliseconds
     */
    public static String timeFormat(long milliseconds) {
        String daysStr;
        String hoursStr;
        String minutesStr;
        String secondsStr;
        //天
        long day = (milliseconds / 1000) / (24 * 3600);
        if (day < 10) {
            daysStr = "0" + day;
        } else {
            daysStr = day + "";
        }
        //时
        long hour = ((milliseconds / 1000) % (24 * 3600)) / 3600;
        if (hour < 10) {
            hoursStr = "0" + hour;
        } else {
            hoursStr = hour + "";
        }
        //分
        long minute = ((milliseconds / 1000) % 3600) / 60;
        if (minute < 10) {
            minutesStr = "0" + minute;
        } else {
            minutesStr = minute + "";
        }
        //秒
        long second = (milliseconds / 1000) % 60;
        if (second < 10) {
            secondsStr = "0" + second;
        } else {
            secondsStr = second + "";
        }

        return String.format("%s天%s小时%s分%s秒",daysStr,hoursStr,minutesStr,secondsStr);
    }
}
