package com.raru.model.data;

public class Task {
    private final int id;
    private final int arrivalTime;
    protected int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
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

        public void decreaseServiceTime() {
            this.serviceTime--;
        }

        @Override
        public MutableTask toMutable() {
            return this;
        }
    }
}
