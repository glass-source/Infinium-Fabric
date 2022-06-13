package com.infinium.api.config;

import com.infinium.Infinium;
import com.mojang.datafixers.util.Pair;

public class InfiniumConfig {
    public static SimpleConfig CONFIG;
    private static InfiniumConfigProvider configs;

    public static String START_DATE;
    public static long ECLIPSE_TIME_LEFT;
    public static long ECLIPSE_TOTAL_TIME;


    public static void initConfig() {
        configs = new InfiniumConfigProvider();
        createConfigs();
        CONFIG = SimpleConfig.of(Infinium.MOD_ID + "config").provider(configs).request();
        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("start.date", "2022-06-01"));
        configs.addKeyValuePair(new Pair<>("eclipse.time.left", 0));
        configs.addKeyValuePair(new Pair<>("eclipse.time.total", 0));

    }

    private static void assignConfigs() {
        START_DATE = CONFIG.getOrDefault("start.date", "2022-06-01");
        ECLIPSE_TIME_LEFT = CONFIG.getOrDefault("eclipse.time.left", 0);
        ECLIPSE_TOTAL_TIME = CONFIG.getOrDefault("eclipse.time.total", 0);


        System.out.println("All " + configs.getConfigsList().size() + " config values have been set properly");
    }
}
