package com.infinium.global.config.data.world;

import java.time.LocalDate;

public class WorldData {
    
     private String startDate;
     private String currentDate;
     private long endsIn;
     private long totalTime;
     private long lastTimeChecked;
     private boolean canStartEclipse;

     public WorldData(String startDate, long eclipseEndTime, long eclipseTotalTime, long eclipseLastChecked, boolean canStartEclipse) {
         this.startDate = startDate;
         this.currentDate = LocalDate.now().toString();
         this.endsIn = eclipseEndTime;
         this.totalTime = eclipseTotalTime;
         this.lastTimeChecked = eclipseLastChecked;
         this.canStartEclipse = canStartEclipse;
     }


}
