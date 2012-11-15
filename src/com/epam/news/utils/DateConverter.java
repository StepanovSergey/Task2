package com.epam.news.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class provides conversion between java.util.Date and java.sql.Date in
 * two sides
 * 
 * @author Siarhei_Stsiapanau
 * 
 */
public class DateConverter {
    /**
     * Convert java.sql.Date to java.util.Date
     * 
     * @param sqlDate
     *            java.sql.Date to convert
     * @return java.util.Date date
     */
    public static java.util.Date convertToDateUtil(java.sql.Date sqlDate) {
	long timeInMillis = sqlDate.getTime();
	return new java.util.Date(timeInMillis);
    }

    /**
     * Convert java.util.Date to java.sql.Date
     * 
     * @param utilDate
     *            java.util.Date to convert
     * @return java.sql.Date date
     */
    public static java.sql.Date convertToDateSql(java.util.Date utilDate) {
	long timeInMillis = utilDate.getTime();
	return new java.sql.Date(timeInMillis);
    }

    /**
     * Convert java.util.Date to string. If pattern is null or it's length = 0,
     * then pattern will be given by default locale
     * 
     * @param utilDate
     *            java.util.Date to convert
     * @param pattern
     *            date pattern
     * @return string representation of date in given pattern
     */
    public static String convertToString(java.util.Date utilDate, String pattern) {
	DateFormat dateFormat = null;
	if (isValidPattern(pattern)) {
	    dateFormat = new SimpleDateFormat();
	} else {
	    dateFormat = new SimpleDateFormat(pattern);
	}
	return dateFormat.format(utilDate);
    }

    /**
     * Convert java.sql.Date to string. If pattern is null or it's length = 0,
     * then pattern will be given by default locale
     * 
     * @param sqlDate
     *            java.sql.Date to convert
     * @param pattern
     *            date pattern
     * @return string representation of date in given pattern
     */
    public static String convertToString(java.sql.Date sqlDate, String pattern) {
	java.util.Date utilDate = convertToDateUtil(sqlDate);
	return convertToString(utilDate, pattern);
    }

    public static java.util.Date convertToDateUtil(String dateString, String pattern) {
	DateFormat dateFormat = null;
	java.util.Date date = null;
	if (isValidPattern(pattern)) {
	    dateFormat = new SimpleDateFormat();
	} else {
	    dateFormat = new SimpleDateFormat(pattern);
	}
	try {
	    date = dateFormat.parse(dateString);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return date;
    }

    public static java.sql.Date convertToDateSql(String dateString, String pattern) {
	java.util.Date utilDate = convertToDateUtil(dateString, pattern);
	return convertToDateSql(utilDate);
    }
        
    private static boolean isValidPattern(String pattern){
	if (pattern.isEmpty() || pattern == null) {
	    return false;
	} else {
	    return true;
	}
    }

}
