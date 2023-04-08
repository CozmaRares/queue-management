package com.raru.model;

import java.util.UUID;

public class Task {
    private final String id;
    private final int arrivalTime;
    private int serviceTime;

    public Task(int arrivalTime, int serviceTime) {
        this.id = UUID.randomUUID().toString();
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public String getId() {
        return this.id;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getServiceTime() {
        return this.serviceTime;
    }

    public void decreaseServiceTime(int delta) {
        this.serviceTime -= delta;
    }

    public void decreaseServiceTime() {
        this.decreaseServiceTime(1);
    }

}
