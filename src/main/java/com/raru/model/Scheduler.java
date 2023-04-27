package com.raru.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.raru.model.data.SimulationFrame;
import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.model.strategy.PartitionStrategy;
import com.raru.model.strategy.ShortestQueueStrategy;
import com.raru.model.strategy.ShortestWaitingTimeStrategy;

public class Scheduler {
    private List<Server> servers;
    private List<Thread> threads;
    private PartitionStrategy strategy;

    public Scheduler(int numberOfServers, PartitionPolicy policy) {
        servers = new ArrayList<>(numberOfServers);
        threads = new ArrayList<>(numberOfServers);

        switch (policy) {
            case SHORTEST_QUEUE -> strategy = new ShortestQueueStrategy();
            case SHORTEST_WAITING_TIME -> strategy = new ShortestWaitingTimeStrategy();
        }

        for (int i = 0; i < numberOfServers; i++) {
            var server = new Server();

            servers.add(server);

            var t = new Thread(server);
            threads.add(t);
            t.start();
        }
    }

    public boolean dispatchTask(Task task) {
        return strategy.addTask(servers, task);
    }

    public void stop() {
        servers.forEach(s -> s.stop());

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public SimulationFrame takeSnapshot(List<Task> remainingTasks, Collection<Task> waitingTasks, int simulationTime) {
        var frame = new SimulationFrame(remainingTasks, waitingTasks, simulationTime);

        servers.forEach(server -> frame.addQueue(server.getTasks(), server.getWaitingTime()));

        return frame;
    }
}
