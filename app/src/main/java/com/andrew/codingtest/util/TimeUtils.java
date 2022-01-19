package com.andrew.codingtest.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by andrew.liu on 2022/1/19.
 */
public class TimeUtils {
    // Use device default TimeZone
    public final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    public final static SimpleDateFormat DateTimeFormatWithHourMinute = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
    public final static SimpleDateFormat DateTimeFormatWithHourMinuteSecond = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
    public final static SimpleDateFormat DateTimeFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

    public static long HOUR_TIME_MILLS = 60 * 60 * 1000L;
    public static long DATE_TIME_MILLS = 24 * 60 * 60 * 1000L;

    public static String getDateTimeFormatWithHourMinuteSecond(long now) {
        DateTimeFormatWithHourMinuteSecond.setTimeZone(DEFAULT_TIME_ZONE);
        return DateTimeFormatWithHourMinuteSecond.format(now);
    }

    public static String getDateTimeFormatWithHourMinute(long now) {
        DateTimeFormatWithHourMinute.setTimeZone(DEFAULT_TIME_ZONE);
        return DateTimeFormatWithHourMinute.format(now);
    }

    public static String getDateTimeFormat(long now) {
        DateTimeFormat.setTimeZone(DEFAULT_TIME_ZONE);
        return DateTimeFormat.format(now);
    }

}
