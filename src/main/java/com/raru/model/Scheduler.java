package com.raru.model;

import java.util.ArrayList;
import java.util.List;

import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.model.strategy.PartitionStrategy;
import com.raru.model.strategy.ShortestQueueStrategy;
import com.raru.model.strategy.ShortestWaitingTimeStrategy;

public class Scheduler {
    private List<Server> servers;
    private PartitionStrategy strategy;

    public Scheduler(int numberOfServers, PartitionPolicy policy) {
        servers = new ArrayList<>(numberOfServers);

        for (int i = 0; i < numberOfServers; i++) {
            var server = new Server();

            servers.add(server);

            new Thread(server).start();
        }

        this.setStrategy(policy);
    }

    public void setStrategy(PartitionPolicy policy) {
        switch (policy) {
            case SHORTEST_QUEUE -> this.strategy = new ShortestQueueStrategy();
            case SHORTEST_WAITING_TIME -> this.strategy = new ShortestWaitingTimeStrategy();
        }
    }

    public void dispatchTask(Task task) {
        this.strategy.addTask(servers, task);
    }

    public void stop() {
        this.servers.forEach(s -> s.stop());
    }
}
