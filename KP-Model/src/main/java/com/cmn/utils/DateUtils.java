package com.cmn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static Date getString2Date(String dateStr, String dateFormatStr) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
		return dateFormat.parse(dateStr);
	}

	public static String getNow2String(String dateFormatStr) throws Exception{
        return getDate2String(new Date(), dateFormatStr);

    }

	public static String getDate2String(Date date, String dateFormatStr) throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        return dateFormat.format(date);
    }

	public static long getTimeSpan(Date startDate, Date endDate) throws Exception{
	    return endDate.getTime() - startDate.getTime();
	}
	public static long getTimeSpanSec(Date startDate, Date endDate) throws Exception{
	    return getTimeSpan(startDate, endDate) / 1000L;
	}

	public static Date addDay(Date stdDate, int addDay) throws Exception{
		Calendar c = Calendar.getInstance();
        c.setTime(stdDate);

        c.add(Calendar.DATE, addDay);

        return c.getTime();

	}

}