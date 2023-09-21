package com.project.crypto.model;

public enum Interval {
    ONE_MINUTE("1m"),
    FIVE_MINUTES("5m"),
    ONE_HOUR("1h");

    private String interval;
    Interval(String s){
        interval=s;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
