package com.hour.tools;

public class MyTime {

    public static String formatMVTime(double thisTime, double allTime, int type) {
        String thistime = String.format("%02d:%02d:%02d", (int) thisTime / 3600, (int) thisTime / 60,
                (int) thisTime % 60);
        String alltime = String.format("%02d:%02d:%02d", (int) allTime / 3600, (int) allTime / 60, (int) allTime % 60);
        return thistime + "/" + alltime;
    }

}
