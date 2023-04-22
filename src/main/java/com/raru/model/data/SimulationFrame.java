package com.raru.model.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationFrame {
    private final List<List<Task>> queues;
    private final List<Task> remainingTasks;
    private int simulationTime;
    private boolean isEmpty;

    public SimulationFrame(Collection<Task> remainingTasks, int simulationTime) {
        this.queues = new ArrayList<>();
        this.remainingTasks = new ArrayList<>(remainingTasks);
        this.simulationTime = simulationTime;
        this.isEmpty = remainingTasks.size() == 0;
    }

    public void addQueue(List<Task> queue) {
        this.isEmpty = this.isEmpty && queue.size() == 0;

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

    public boolean isEmpty() {
        return this.isEmpty;
    }
}
