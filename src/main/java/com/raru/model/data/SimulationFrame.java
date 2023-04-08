package com.raru.model.data;

import java.util.ArrayList;
import java.util.List;

import com.raru.model.data.Task.ReadOnlyTask;

public class SimulationFrame {
    private final List<List<ReadOnlyTask>> queues;

    public SimulationFrame() {
        queues = new ArrayList<>();
    }

    public void addQueue(List<ReadOnlyTask> queue) {
        queues.add(queue);
    }
}
