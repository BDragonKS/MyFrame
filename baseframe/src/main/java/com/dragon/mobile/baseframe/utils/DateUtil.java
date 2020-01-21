package com.dragon.mobile.baseframe.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class DateUtil {

    private static String currentSimpleDateStr;

    private static class DateUtilHolder {
        static final DateUtil INSTANCE = new DateUtil();
    }

    private DateUtil() {
    }

    public static DateUtil getInstance() {
        return DateUtilHolder.INSTANCE;
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当天时间开始
     *
     * @return yyyy-MM-dd 00:00:00
     */
    public static String getCurrentDateStartStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当日
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentSimpleDateStr() {
        if (TextUtils.isEmpty(currentSimpleDateStr)) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            currentSimpleDateStr = formatter.format(Calendar.getInstance().getTime());
        }
        return currentSimpleDateStr;
    }

    /**
     * 获取昨日
     *
     * @return yyyy-MM-dd
     */
    public static String getYesterdaySimpleDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取给定日期前一日
     *
     * @param date 日期参数
     * @return yyyy-MM-dd
     */
    public static String getBeforeDaySimpleDateStr(String date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 2) {
                int year = Integer.valueOf(dates[0]);
                int month = Integer.valueOf(dates[1]) - 1;
                int day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取当前月份
     *
     * @return yyyy-MM
     */
    public static String getCurrentMonthStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前年份
     *
     * @return yyyy
     */
    public static String getCurrentYearStr() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * 获取当前月份最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentMonthLastDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取上一个月最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeMonthLastDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取指定月份最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getMonthLastDayStr(int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取当前时间前一个月日期
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeOneMonthStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取当前时间前一年日期
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeOneYearStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取指定月份
     *
     * @return yyyy-MM
     */
    public static String getMonthStr(int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 将毫秒转为时分秒
     *
     * @param duration 毫秒数
     * @return 时分秒
     */
    public static String timeParse(long duration) {
        String time = "";
        long hour = duration / 3600000;

        long minute = (duration % 3600000) / 60000;
        if (hour < 10) {
            time += "0";
        }
        time += hour + "时";
        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";
        return time;
    }

    /**
     * 将秒转为时分秒
     *
     * @param second 秒数
     * @return 时分秒
     */
    public static String second2Parse(long second) {
        String time = "";
        long hour = second / 3600;

        long minute = (second % 3600) / 60;
        if (hour < 10) {
            time += "0";
        }
        time += hour + "时";
        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";
        return time;
    }

    /**
     * 将毫秒转为具体日期
     *
     * @param millisecond 时间戳
     * @return yyyy-MM-dd格式
     */
    public static String convert2Date(long millisecond) {
        try {
            Date date = new Date(millisecond);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将毫秒转为具体日期
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String convert2Date1(long millisecond) {
        try {
            Date date = new Date(millisecond);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String转long型
     *
     * @param strDate 字符串
     * @return 时间戳
     */
    public static long convert2Time(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = formatter.parse(strDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前月的月初
     *
     * @return 字符串
     */
    public static String getCurrentMonthStart() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01 00:00:00", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间一周的日期
     *
     * @return 字符串
     */
    public static String getBeforeCurrent7() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间一周的日期
     *
     * @return 字符串
     */
    public static String getCurrentYear() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        return calendar.get(Calendar.YEAR) + "-01" + "-01";
    }

    /**
     * 获取当前年份
     * @return 年份
     */
    public int queryCurrentYear() {
        return Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @return 月份
     */
    public int queryCurrentMonth() {
        return Calendar.getInstance(Locale.CHINA).get(Calendar.MONTH) + 1;
    }


    /**
     * 默认当前时间的时间选择器
     *
     * @param context         当前activity上下文对象
     * @param dateSetListener 选择回调监听
     */
    public void showDatePicker(Context context,
                               DatePickerDialog.OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog;

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        datePickerDialog.show();
    }

    /**
     * 显示选择日期的对话框
     * @param context 上下文对象
     * @param dateSetListener 日期返回监听
     * @param date 默认日期
     * @param minDate 最小日期
     */
    public void showDatePicker(Context context,
                               DatePickerDialog.OnDateSetListener dateSetListener, String date, String minDate) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 2) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(minDate)) {

            Calendar calendarMin = Calendar.getInstance(Locale.CHINA);
            String[] dates = date.split("-");
            if (dates.length > 2) {
                calendarMin.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    /**
     * 显示选择日期的对话框
     * @param context 上下文对象
     * @param dateSetListener 日期返回监听
     * @param date 默认日期
     * @param maxDate 最大日期
     */
    public void showDatePickerHaveMax(Context context,
                                      DatePickerDialog.OnDateSetListener dateSetListener, String date, String maxDate) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 2) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(maxDate)) {

            Calendar calendarMax = Calendar.getInstance(Locale.CHINA);
            String[] dates = maxDate.split("-");
            if (dates.length > 2) {
                calendarMax.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMaxDate(calendarMax.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    /**
     * <b>方法描述： 显示选择月份的对话框</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-4-14下午2:03:47
     *
     * @param date 字符串的日期格式（yyyy-MM） null默认显示当前日期
     * @since Met 1.0
     */
    public void showDatePickerDissmissDay(Context context,
                                          DatePickerDialog.OnDateSetListener dateSetListener, String date, String minDate) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 1) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
//                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(minDate)) {

            Calendar calendarMin = Calendar.getInstance(Locale.CHINA);
            String[] dates = date.split("-");
            if (dates.length > 2) {
                calendarMin.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
        }
        datePickerDialog.show();
        ((ViewGroup) ((ViewGroup) (datePickerDialog.getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(1)
                .setVisibility(View.GONE);

        /*//只显示年月，隐藏掉日
        DatePicker dp = findDatePicker((ViewGroup) datePickerDialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup)dp.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE);
            //如果想隐藏掉年，将getChildAt(2)改为getChildAt(0)
        }*/
    }

}
