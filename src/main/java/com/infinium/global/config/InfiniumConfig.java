package com.infinium.global.config;

import com.infinium.Infinium;
import com.infinium.server.eclipse.SolarEclipseManager;
import eu.midnightdust.lib.config.MidnightConfig;

public class InfiniumConfig extends MidnightConfig {
    private static final SolarEclipseManager manager = Infinium.getInstance().getCore().getEclipseManager();
    @Entry public static String startDate = "2022-08-01";

}