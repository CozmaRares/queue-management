package com.raru.model.data;

public class Task {
    private static int counterID = 0;

    private final int id;
    private final int arrivalTime;
    private int serviceTime;

    public Task(int arrivalTime, int serviceTime) {
        this.id = Task.counterID++;

        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", this.id, this.arrivalTime, this.serviceTime);
    }
}
