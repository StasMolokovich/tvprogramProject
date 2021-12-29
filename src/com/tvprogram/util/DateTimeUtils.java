package com.tvprogram.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private static final String dateTime = "dd.MM.yyyy HH:mm";
    private static final String date = "dd.MM.yyyy";

    private static final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(dateTime);

    public static Date strToTime(String str) {
        try {
            Date date = simpleDateTimeFormat.parse(str);
            return date;
        } catch (ParseException e) {
            System.out.println("Неверный формат времени!");
        }
        return null;
    }

    public static String timeToStr(Date time) {
        try {
            String timeStr = simpleDateTimeFormat.format(time);
            return timeStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

