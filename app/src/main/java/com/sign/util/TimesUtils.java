package com.sign.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * time 工具类
 */

public class TimesUtils {
    public static int THEME_DEVICE_DEFAULT_LIGHT = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
    public static int THEME_DEVICE_DEFAULT_DARK = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
    public static int THEME_TRADITIONAL = AlertDialog.THEME_TRADITIONAL;
    public static int THEME_HOLO_LIGHT = AlertDialog.THEME_HOLO_LIGHT;
    public static int THEME_HOLO_DARK = AlertDialog.THEME_HOLO_DARK;
    private static DatePickerDialog mDatePickerDialog;//日期选择器

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String get_MM_dd(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd
     */
    public static String get_yyyy_MM_dd(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd HH:mm:ss:SSS
     */
    public static Long getLongTimeOfSSS(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd HH:mm:ss
     */
    public static Long getLongTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将字符串时间转为Long时间
     *
     * @param time yyyy-MM-dd
     */
    public static Long getLongTimeOfYMD(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getStringTimeOfSSS(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStringTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd
     */
    public static String getStringTimeOfYMD(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 将Long时间转成String时间
     *
     * @return yyyy-MM-dd
     */
    public static String getMMddHHmm(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * 当前的时间(设备)
     *
     * @return yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getDeviceTimeOfSSS() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date().getTime() + "";//当前时间的long字符串
        }
        return date;
    }

    /**
     * 当前的时间(设备)
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getMMddHHmm() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date().getTime() + "";//当前时间的long字符串
        }
        return date;
    }

    /**
     * 当前的时间(年月日)
     *
     * @return yyyy-MM-dd
     */
    public static String getDeviceTimeOfYMD() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前的时间(年月)
     *
     * @return yyyy-MM
     */
    public static String getDeviceTimeOfYM() {
        String date = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            date = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某月最后一天(年月日)
     *
     * @return yyyy-MM
     */
    public static String getLastDayOfMonthOfYMD(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    /**
     * 获取某月最后一天(日)
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return lastDay;
    }

    /**
     * 显示日期选择器
     *
     * @param themeLight true 白色背景; false 黑色背景
     */
    public static TimesUtils showDatePickerDialog(Context context, boolean themeLight, String title, int year, int month, int day, OnDatePickerListener onDateTimePickerListener) {
        int themeId = AlertDialog.THEME_HOLO_LIGHT;//默认白色背景
        if (!themeLight) {
            themeId = AlertDialog.THEME_HOLO_DARK;//黑色背景
        }
        return showDatePickerDialog(context, themeId, title, year, month, day, onDateTimePickerListener);
    }

    /**
     * 显示日期选择器, 默认白色背景
     */
    public static TimesUtils showDatePickerDialog(Context context, String title, int year, int month, int day, OnDatePickerListener onDateTimePickerListener) {
        return showDatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, title, year, month, day, onDateTimePickerListener);
    }

    /**
     * 显示日期选择器
     */
    public static TimesUtils showDatePickerDialog(Context context, int themeId, String title, int year, int month, int day,
                                                  final OnDatePickerListener onDateTimePickerListener) {
        mDatePickerDialog = new DatePickerDialog(context, themeId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;//月份加一
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onConfirm(year, month, dayOfMonth);
                }
            }

        }, year, month - 1, day);//月份减一

        mDatePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onCancel();
                }
            }
        });

        if (!TextUtils.isEmpty(title)) {
            mDatePickerDialog.setTitle(title);
        }
        mDatePickerDialog.show();
        return new TimesUtils();
    }

    /**
     * 隐藏年, 只显示月和日
     */
    public void setYearGone() {
        setSpecialDatePicker(1);
    }

    /**
     * 隐藏日, 只显示年和月
     */
    public void setDayGone() {
        setSpecialDatePicker(2);
    }

    private void setSpecialDatePicker(int state) {
        try {
            DatePicker dp = mDatePickerDialog.getDatePicker();

            NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
            NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
            NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度

            //年的最大值为2100
            //月的最大值为11
            //日的最大值为28,29,30,31
            int value0 = view0.getMaxValue();//获取第一个View的最大值
            int value1 = view1.getMaxValue();//获取第二个View的最大值
            int value2 = view2.getMaxValue();//获取第三个View的最大值

            if (state == 1) {//隐藏年, 只显示月和日
                if (value0 > 252) {
                    view0.setVisibility(View.GONE);
                } else if (value1 > 252) {
                    view1.setVisibility(View.GONE);
                } else if (value2 > 252) {
                    view2.setVisibility(View.GONE);
                }
            } else if (state == 2) {//隐藏日, 只显示年和月
                if (value0 > 25 && value0 < 252) {
                    view0.setVisibility(View.GONE);
                } else if (value1 > 25 && value1 < 252) {
                    view1.setVisibility(View.GONE);
                } else if (value2 > 25 && value2 < 252) {
                    view2.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示时间选择器
     */
    public static void showTimerPickerDialog(Context context, boolean themeLight, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        int themeId = AlertDialog.THEME_HOLO_LIGHT;//默认白色背景
        if (!themeLight) {
            themeId = AlertDialog.THEME_HOLO_DARK;//黑色背景
        }
        showTimerPickerDialog(context, themeId, title, hourOfDay, minute, is24HourView, onTimerPickerListener);
    }

    /**
     * 显示时间选择器, 默认白色背景
     */
    public static void showTimerPickerDialog(Context context, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        showTimerPickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, title, hourOfDay, minute, is24HourView, onTimerPickerListener);
    }

    /**
     * 显示时间选择器
     */
    public static void showTimerPickerDialog(Context context, int themeId, String title, int hourOfDay, int minute, boolean is24HourView, final OnTimerPickerListener onTimerPickerListener) {
        TimePickerDialog dialog = new TimePickerDialog(context, themeId, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (onTimerPickerListener != null) {
                    onTimerPickerListener.onConfirm(hourOfDay, minute);
                }
            }
        }, hourOfDay, minute, is24HourView);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (onTimerPickerListener != null) {
                    onTimerPickerListener.onCancel();
                }
            }
        });

        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.show();
    }

    /**
     * @return 返回当前时间！
     */
    public static String getCurruntTime() {
        return System.currentTimeMillis() + "";
    }

    /**
     * 日期选择器监听
     */
    public interface OnDatePickerListener {
        void onConfirm(int year, int month, int dayOfMonth);

        void onCancel();
    }

    /**
     * 时间选择器监听
     */
    public interface OnTimerPickerListener {
        void onConfirm(int hourOfDay, int minute);

        void onCancel();
    }

    /**
     * 根据当前日期获得是周几
     * time=yyyy-MM-dd
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDays(Date date1, Date date2) {
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDays(long date1, long date2) {
        int days = (int) ((date1 - date2) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 得到显示多少天前！
     *
     * @param timeStr
     * @return
     */
    public static String getStandardDate(long timeStr) {
        String temp = "";
        try {
            long now = System.currentTimeMillis();
            long diff = (now - timeStr) / 1000;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
            if (months > 0) {
                temp = months + "月前";//这是显示月份
                temp = getStringTime(timeStr);//这是直接显示时间
            } else if (days > 0) {
                temp = days + "天前";
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else if (minutes >= 1) {
                temp = minutes + "分钟前";
            } else {
                temp = "1分钟前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
