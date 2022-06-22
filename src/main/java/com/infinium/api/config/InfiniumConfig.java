package com.infinium.api.config;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.Date;

public class InfiniumConfig extends CustomMidnightConfig {


    @Entry public static long ECLIPSE_TIME_LEFT = 0;
    @Entry public static String START_DATE = "2022-06-06";
    @Entry public static long ECLIPSE_TIME_TOTAL = 0;
    @Entry public static long CLIENT_PAUSE_DATE = new Date().getTime();
}
