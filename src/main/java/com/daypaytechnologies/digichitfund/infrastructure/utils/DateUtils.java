package com.daypaytechnologies.digichitfund.infrastructure.utils;

import lombok.experimental.UtilityClass;

import java.util.Calendar;

@UtilityClass
public class DateUtils {

    public String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return String.valueOf(year);
    }
}
