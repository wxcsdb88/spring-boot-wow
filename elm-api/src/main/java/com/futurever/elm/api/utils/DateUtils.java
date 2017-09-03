package com.futurever.elm.api.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by wxcsdb88 on 2017/8/13 15:22.
 */
public class DateUtils {
    public static Date timestampToDateTime(String timestamp) {
        Long timestampLong = Long.valueOf(timestamp);
        DateTime dateTime = new DateTime(timestampLong);
        return dateTime.toDate();
    }

    public static Date currentDateTime() {
        return new DateTime().toDate();
    }

    public static String timestampToDateTimeStr(String timestamp, String format) {
        Long timestampLong = Long.valueOf(timestamp);
        DateTime dateTime = new DateTime(timestampLong);
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return dateTime.toString(format);
    }

    public static String currentDateTimeStr(String format) {
        DateTime dateTime = new DateTime();
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return dateTime.toString(format);
    }

    public static Long currentTimestamp() {
        return DateTime.now().getMillis();
    }

    public static String currentTimestampStr() {
        return DateTime.now().getMillis() + "";
    }

    public static String datetimeToTimestampStr(String datetime, String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = DateTime.parse(datetime, dateTimeFormatter);
        return dateTime.getMillis() + "";
    }

    public static void main(String[] args) {

        Date date = DateUtils.timestampToDateTime("1493952525730");
        String dateTimeStr = DateUtils.timestampToDateTimeStr("1493952525730", null);
        System.out.println(date);
        System.out.println(dateTimeStr);
        System.out.println(new Date());
        System.out.println(currentTimestamp());
        System.out.println(datetimeToTimestampStr("2017-06-12 13:10:12", "yyyy-MM-dd HH:mm:ss"));
    }
}
