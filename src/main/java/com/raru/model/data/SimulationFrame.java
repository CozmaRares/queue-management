package com.raru.model.data;

import java.util.ArrayList;
import java.util.List;

import com.raru.model.data.Task.ImmutableTask;

public class SimulationFrame {
    private final List<List<ImmutableTask>> queues;
    private final List<ImmutableTask> tasks;

    public SimulationFrame(List<ImmutableTask> remainingTasks) {
        queues = new ArrayList<>();
        tasks = remainingTasks;
    }

    public void addQueue(List<ImmutableTask> queue) {
        queues.add(queue);
    }

    public List<List<ImmutableTask>> getQueues() {
        return queues;
    }

    public List<ImmutableTask> getRemainingTasks() {
        return tasks;
    }
}
