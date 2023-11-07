package com.zootopia.userservice.util;

import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DateUtil {

    public static long toHours(int hours) {
        return 1000L * 60 * 60 * hours;
    }

    public static long toDays(int days) {
        return 1000L * 60 * 60 * 24 * days;
    }

    public static Date toDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration);
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
