package com.carrental.bookingservice.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date getToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getTomorrow() {
        Calendar c = Calendar.getInstance();
        c.setTime(getToday());
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static Date getTomorrow(Date lastDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
}
