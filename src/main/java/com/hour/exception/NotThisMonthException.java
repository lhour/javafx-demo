package com.hour.exception;

public class NotThisMonthException extends Exception {

    public NotThisMonthException() {
    }

    public NotThisMonthException(String s) {
        super(s);
    }
}
