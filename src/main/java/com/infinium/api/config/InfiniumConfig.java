package com.infinium.api.config;

import com.infinium.Infinium;
import com.infinium.api.events.eclipse.SolarEclipse;
import com.mojang.datafixers.util.Pair;

public class InfiniumConfig {
    private static SimpleConfig SIMPLE_CONFIG;
    public static InfiniumConfigProvider CONFIG_PROVIDER;

    public static String START_DATE;
    public static long ECLIPSE_TIME_LEFT;
    public static long ECLIPSE_TIME_TOTAL;
    public static long ECLIPSE_TIME_PAUSED;

    public static void initConfig() {
        CONFIG_PROVIDER = new InfiniumConfigProvider();
        createConfigs();
        SIMPLE_CONFIG = SimpleConfig.of(Infinium.MOD_ID + "config").provider(CONFIG_PROVIDER).request();
        assignConfigs();
    }

    private static void createConfigs() {
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("start.date", "2022-06-01"));
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("eclipse.time.left", SolarEclipse.getTimeToEnd()));
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("eclipse.time.total", SolarEclipse.getTotalTime()));
    }

    private static void assignConfigs() {
        START_DATE = SIMPLE_CONFIG.getOrDefault("start.date", "2022-06-01");
        ECLIPSE_TIME_LEFT = (long) SIMPLE_CONFIG.getOrDefault("eclipse.time.left", SolarEclipse.getTimeToEnd());
        ECLIPSE_TIME_TOTAL = (long) SIMPLE_CONFIG.getOrDefault("eclipse.time.total", SolarEclipse.getTotalTime());

        System.out.println("All " + CONFIG_PROVIDER.getConfigsList().size() + " config values have been set properly");
    }

    public static void set(String path, Object newValue) {
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>(path, newValue));
    }

}
