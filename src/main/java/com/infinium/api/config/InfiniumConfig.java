package com.infinium.api.config;

import com.infinium.Infinium;
import com.infinium.server.eclipse.SolarEclipseManager;
import eu.midnightdust.lib.config.MidnightConfig;

public class InfiniumConfig extends MidnightConfig {

    private static final SolarEclipseManager manager = Infinium.getInstance().getCore().getEclipseManager();

    @Entry public static String startDate = "2022-08-01";
    @Entry public static long endsIn = manager.getTimeToEnd();
    @Entry public static long totalTime = manager.getTotalTime();
    @Entry public static long lastTimeChecked = manager.getLastTimeChecked();

}