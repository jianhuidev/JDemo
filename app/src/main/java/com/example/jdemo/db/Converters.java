package com.example.jdemo.db;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converters {

    @TypeConverter
    public long calendarToDateStamp(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar dateStampToCalendar(long v) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(v);
        return c;
    }
}
