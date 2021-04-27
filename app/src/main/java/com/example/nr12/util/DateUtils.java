package com.example.nr12.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fabiano on 22/09/2017.
 */

public class DateUtils {

    public static String dateToString(int year, int month, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date data = calendar.getTime();
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(data);
        return dt;
    }

    public static String dateToString(Date data){

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dt = format.format(data);
        return dt;
    }


    public static Date getDate(int year, int month, int dayOfMonth){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date data = calendar.getTime();
        return data;
    }


}
