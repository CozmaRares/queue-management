package com.raru.model.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationFrame {
    private final List<List<Task>> queues;
    private final List<Task> remainingTasks;
    private int simulationTime;

    public SimulationFrame(Collection<Task> remainingTasks, int simulationTime) {
        this.queues = new ArrayList<>();
        this.remainingTasks = new ArrayList<>(remainingTasks);
        this.simulationTime = simulationTime;
    }

    public void addQueue(List<Task> queue) {
        this.queues.add(queue);
    }

    public List<List<Task>> getQueues() {
        return this.queues;
    }

    public List<Task> getRemainingTasks() {
        return this.remainingTasks;
    }

    public int getSimulationTime() {
        return this.simulationTime;
    }
}
