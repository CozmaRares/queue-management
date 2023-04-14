package com.raru.model.data;

public class Task {
    private static int counterID = 0;

    protected int id;
    protected int arrivalTime;
    protected int serviceTime;

    private Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

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

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", this.id, this.arrivalTime, this.serviceTime);
    }

    public MutableTask toMutable() {
        return new MutableTask(id, arrivalTime, serviceTime);
    }

    public static class MutableTask extends Task {
        protected MutableTask(int id, int arrivalTime, int serviceTime) {
            super(id, arrivalTime, serviceTime);
        }

        public MutableTask(int arrivalTime, int serviceTime) {
            super(arrivalTime, serviceTime);
        }

        public void decreaseServiceTime(int delta) {
            this.serviceTime -= delta;
        }

        public void decreaseServiceTime() {
            this.decreaseServiceTime(1);
        }
    }
}
