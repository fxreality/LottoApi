package com.lapi.lottoapi.lottoguess;

public class Statistics {
    private final long id;
    private final int history;
    private final int average;

    public Statistics(long id, int history, int average) {
        this.id = id;
        this.history = history;
        this.average = average;
    }

    public long getId() {
        return id;
    }

    public int getHistory() {
        return history;
    }

    public int getAverage() {
        return average;
    }
}
