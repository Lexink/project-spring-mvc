package com.javarush.domain;

public enum Status {
    IN_PROGRESS("IN_PROGRESS"),
    DONE ("DONE"),
    PAUSED ("PAUSED");

    private final String statusName;

    private Status (String statusName){
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
