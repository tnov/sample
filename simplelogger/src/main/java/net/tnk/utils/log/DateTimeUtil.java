/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tnk.utils.log;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 *
 * @author fission
 */
public class DateTimeUtil {

    public static String LOCAL_DATE_TIME = "local";

    public static String ZONED_DATE_TIME = "zoned";

    public static final String DEFAULT_DATE_TIME = LOCAL_DATE_TIME;

    public static final String DATE_TIME_IN_MILLS_FORMAT_FLAT = "yyyyMMddHHmmssSSS";
    public static final String DATE_TIME_IN_MILLS_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String DATE_TIME_FORMAT_FLAT = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMAT_FLAT = "yyyyMMdd";
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String TIME_IN_MILLS_FORMAT_FLAT = "HHmmssSSS";
    public static final String TIME_IN_MILLS_FORMAT = "HH:mm:ss.SSS";
    public static final String TIME_FORMAT_FLAT = "HHmmss";
    public static final String TIME_FORMAT = "HH:mm:ss";

    public static String getCurrentLocalDateString(boolean isFlat) {
    	return getLocalDateString(null,isFlat);
    }
    public static String getCurrentLocalDateTimeString(boolean isFlat) {
    	return getLocalDateTimeString(null,isFlat);
    }
    public static String getCurrentLocalDateTimeInMillsString(boolean isFlat) {
    	return getLocalDateTimeInMillsString(null,isFlat);
    }

    public static String getCurrentZonedDateString(boolean isFlat) {
    	return getZonedDateString(null,isFlat);
    }
    public static String getCurrentZonedDateTimeString(boolean isFlat) {
    	return getZonedDateTimeString(null,isFlat);
    }
    public static String getCurrentZonedDateTimeInMillsString(boolean isFlat) {
    	return getZonedDateTimeInMillsString(null,isFlat);
    }

    public static String getLocalDateString(LocalDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_FORMAT_FLAT:DATE_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(LOCAL_DATE_TIME):date);
    }
    public static String getLocalDateTimeString(LocalDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_TIME_FORMAT_FLAT:DATE_TIME_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(LOCAL_DATE_TIME):date);
    }
    public static String getLocalDateTimeInMillsString(LocalDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_TIME_IN_MILLS_FORMAT_FLAT:DATE_TIME_IN_MILLS_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(LOCAL_DATE_TIME):date);
    }

    public static String getZonedDateString(ZonedDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_FORMAT_FLAT:DATE_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(ZONED_DATE_TIME):date);
    }
    public static String getZonedDateTimeString(ZonedDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_TIME_FORMAT_FLAT:DATE_TIME_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(ZONED_DATE_TIME):date);
    }
    public static String getZonedDateTimeInMillsString(ZonedDateTime date,boolean isFlat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(isFlat?DATE_TIME_IN_MILLS_FORMAT_FLAT:DATE_TIME_IN_MILLS_FORMAT);
        return formatter.format(date==null?getCurrentDateTime(ZONED_DATE_TIME):date);
    }

    private static TemporalAccessor getCurrentDateTime(String locale) {
        TemporalAccessor dateTime = null;
        if (LOCAL_DATE_TIME.equals(locale)) {
            dateTime = LocalDateTime.now();
        } else if (ZONED_DATE_TIME.equals(locale)) {
            dateTime = ZonedDateTime.now();
        }
        return dateTime;
    }
}
