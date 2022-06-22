package com.infinium.api.utils;

import com.infinium.api.config.InfiniumConfig;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    private static final LocalDate actualDate = LocalDate.now();
    public static final LocalDate startDate = LocalDate.parse(String.valueOf(InfiniumConfig.START_DATE));
    private static int day = (int) ChronoUnit.DAYS.between(startDate, actualDate);

    public static int getDay(){
        return day;
    }

    public static void setDay(int newDay){
        day = newDay;
    }

}
