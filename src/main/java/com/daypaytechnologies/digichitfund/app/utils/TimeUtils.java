package com.daypaytechnologies.digichitfund.app.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass

public class TimeUtils {
    public LocalTime convert12to24TimeFormat(String timeStr) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
