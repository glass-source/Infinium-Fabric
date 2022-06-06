package com.infinium.api.utils;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.gen.feature.LakeFeature;

import java.io.ObjectInputFilter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Utils {

    private static final LocalDate actualDate = LocalDate.now();
    private static final LocalDate startDate = LocalDate.parse("2022-05-26");
    private static int day = (int) ChronoUnit.DAYS.between(startDate, actualDate);


    public static int getDay(){
        return day;
    }

    public static void setDay(int newDay){
        day = newDay;
    }

}
