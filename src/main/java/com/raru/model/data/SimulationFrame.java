package com.raru.model.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.raru.utils.Tuple;

public class SimulationFrame {
    private final List<Tuple<List<Task>, Integer>> queues;
    private final List<Task> waitingTasks;
    private final List<Task> remainingTasks;
    private int simulationTime;
    private boolean isEmpty;

    public SimulationFrame(List<Task> remainingTasks, Collection<Task> waitingTasks, int simulationTime) {
        this.queues = new ArrayList<>();
        this.isEmpty = waitingTasks.size() == 0;
        this.simulationTime = simulationTime;
        this.remainingTasks = remainingTasks;
        this.waitingTasks = new ArrayList<>(waitingTasks);
    }

    public void addQueue(List<Task> queue, int waitingTime) {
        this.isEmpty = this.isEmpty && waitingTime == 0;

        this.queues.add(new Tuple<>(queue, waitingTime));
    }

    public List<Tuple<List<Task>, Integer>> getQueues() {
        return this.queues;
    }

    public List<Task> getWaitingTasks() {
        return this.waitingTasks;
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
