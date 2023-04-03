package com.infinium.global.utils;

import com.google.gson.JsonObject;
import com.infinium.Infinium;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    private final Infinium instance;
    private final JsonObject gameData;
    private final LocalDate actualDate;
    private LocalDate startDate;
    private int currentDay;

    public DateUtils(Infinium instance) {
        this.instance = instance;
        this.gameData = this.instance.getCore().getDataManager().getGameData();
        this.actualDate = LocalDate.now();
        this.startDate = gameData.get("startDate") == null ? actualDate: LocalDate.parse(gameData.get("startDate").getAsString());
        this.currentDay = (int) ChronoUnit.DAYS.between(startDate, actualDate);
    }

    public int getCurrentDay(){
        return currentDay;
    }

    public void setCurrentDay(int newDay){
        currentDay = newDay;
        int dayToChange;

        try {
            //Max day is 120
            dayToChange = Math.max(0, Math.min(120, newDay));
        } catch(NumberFormatException ignored) {
            return;
        }

        LocalDate add = LocalDate.now().minusDays(dayToChange);
        int month = add.getMonthValue();
        int day = add.getDayOfMonth();
        String dateString;

        if (month < 10) {
            dateString = add.getYear() + "-0" + month + "-";
        } else {
            dateString = add.getYear() + "-" + month + "-";
        }

        if (day < 10) {
            dateString = dateString + "0" + day;
        } else {
            dateString = dateString + day;
        }

        gameData.addProperty("startDate", String.valueOf(LocalDate.parse(dateString)));
        startDate = LocalDate.parse(dateString);
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
}
