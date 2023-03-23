package com.points.customerrewards.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static Date getOldStartingDate(int month){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }
}
