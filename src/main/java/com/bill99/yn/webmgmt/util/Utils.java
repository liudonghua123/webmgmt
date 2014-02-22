package com.bill99.yn.webmgmt.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static final String ADMIN_ROLE_NAME = "admin";
	public static final String USER_ROLE_NAME = "user";
	
	private static final DateFormat dateWithoutTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static String formatDateTrimTime(Date date) {
		return dateWithoutTimeFormat.format(date);
	}
	
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static final Date defaultStartDateTime = parseDateTime("2000-01-01 00:00:00");
	public static final Date defaultEndDateTime = parseDateTime("2100-01-01 00:00:00");
	public static Date parseDateTime(String dateTimeString) {
		try {
			return dateTimeFormat.parse(dateTimeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
