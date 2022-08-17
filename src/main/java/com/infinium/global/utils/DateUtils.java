package com.infinium.global.utils;

import com.infinium.Infinium;
import com.infinium.api.config.InfiniumConfig;
import eu.midnightdust.lib.config.MidnightConfig;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private static final LocalDate actualDate = LocalDate.now();
    public static LocalDate startDate = LocalDate.parse(String.valueOf(InfiniumConfig.startDate));
    private static int day = (int) ChronoUnit.DAYS.between(startDate, actualDate);

    public static int getDay(){
        return day;
    }

    public static void setDay(int newDay){
        day = newDay;
        int nD;

        try {
            nD = Math.max(0, Math.min(120, newDay));
        } catch(NumberFormatException ignored) {
            return;
        }

        LocalDate add = LocalDate.now().minusDays(nD);
        int month = add.getMonthValue();
        int day = add.getDayOfMonth();
        String s;

        if (month < 10) {
            s = add.getYear() + "-0" + month + "-";
        } else {
            s = add.getYear() + "-" + month + "-";
        }

        if (day < 10) {
            s = s + "0" + day;
        } else {
            s = s + day;
        }

        InfiniumConfig.startDate = String.valueOf(LocalDate.parse(s));
        InfiniumConfig.write(Infinium.MOD_ID);
        startDate = LocalDate.parse(s);
    }
}
