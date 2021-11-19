package com.hour.tools;

import com.hour.exception.NotThisMonthException;

public class MyDate {

    static int[] days = new int[] { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static int getDaysOfMonth(int year, int month) throws NotThisMonthException {
        if (month <= 0 || month > 12) {
            throw new NotThisMonthException("月份超出范围");
        }
        // 非润
        if (year % 4 != 0) {
            return days[month];
        }
        // 非润
        if (year % 100 == 0 && year % 400 != 0) {
            return days[month];
        }
        // 润年2月
        if (month == 2) {
            return 29;
        }
        return days[month];
    }

    public static int getFirstDayOfWeek(int year, int month) throws NotThisMonthException {
        int day = 0;
        for (int i = 1; i < month; i++) {
            day += days[i];
        }
        return (year - 1 + (year - 1) / 400 - (year - 1) / 100 + (year - 1) / 4 + day + 1) % 7;
    }

    // public static void main(String[] args) throws NotThisMonthException {
    // int i = getFirstDayOfWeek(2022, 1);
    // System.out.println(i);
    // }
}
