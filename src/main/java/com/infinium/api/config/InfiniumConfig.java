package com.infinium.api.config;

import com.infinium.Infinium;
import com.mojang.datafixers.util.Pair;

public class InfiniumConfig {
    public static SimpleConfig CONFIG;
    private static InfiniumConfigProvider configs;

    public static String START_DATE;


    public static void initConfig() {
        configs = new InfiniumConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Infinium.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("start.date", "2022-06-01"));

    }

    private static void assignConfigs() {
        START_DATE = CONFIG.getOrDefault("start.date", "2022-06-01");


        System.out.println("All " + configs.getConfigsList().size() + " config values have been set properly");
    }
}
