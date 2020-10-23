package com.peryite.familybudget.dbhelper;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;
import java.sql.Timestamp;

public class Converter {
    @TypeConverter
    public static Timestamp fromTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Timestamp date) {
        return date == null ? null : date.getTime();
    }
}
