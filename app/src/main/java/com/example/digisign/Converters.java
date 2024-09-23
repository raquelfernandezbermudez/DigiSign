package com.example.digisign;

import androidx.room.TypeConverter;
import java.sql.Timestamp;

public class Converters {

    @TypeConverter
    public static Timestamp fromTimestampLong(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long toTimestampLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }
}
