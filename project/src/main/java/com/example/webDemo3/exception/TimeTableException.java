package com.example.webDemo3.exception;

public class TimeTableException extends Exception{
    private String message;

    public TimeTableException() {
    }

    public TimeTableException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
