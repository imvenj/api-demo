package me.venj.apidemo.Utils;

import java.util.Calendar;
import java.util.Date;

public class APIUtils {
    public static long timestampForToday() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        cal.set(year, month, date, 0, 0, 0);
        Date today = cal.getTime();
        long timestamp = today.getTime();
        return timestamp;
    }
}
