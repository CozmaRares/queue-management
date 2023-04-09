package com.raru.model.data;

import java.util.ArrayList;
import java.util.List;

import com.raru.model.data.Task.ImmutableTask;

public class SimulationFrame {
    private final List<List<ImmutableTask>> queues;
    private final List<ImmutableTask> remainingTasks;
    private int simulationTime;

    public SimulationFrame(List<ImmutableTask> remainingTasks, int simulationTime) {
        this.queues = new ArrayList<>();
        this.remainingTasks = remainingTasks;
        this.simulationTime = simulationTime;
    }

    public void addQueue(List<ImmutableTask> queue) {
        this.queues.add(queue);
    }

    public List<List<ImmutableTask>> getQueues() {
        return this.queues;
    }

    public List<ImmutableTask> getRemainingTasks() {
        return this.remainingTasks;
    }

    public int getSimulationTime() {
        return this.simulationTime;
    }
}
