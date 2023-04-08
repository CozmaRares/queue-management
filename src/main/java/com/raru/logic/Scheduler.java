package com.raru.logic;

import java.util.ArrayList;
import java.util.List;

import com.raru.logic.strategy.PartitionPolicy;
import com.raru.logic.strategy.PartitionStrategy;
import com.raru.logic.strategy.ShortestQueueStrategy;
import com.raru.logic.strategy.ShortestTimeStrategy;
import com.raru.model.Server;
import com.raru.model.Task;

public class Scheduler {
    private List<Server> servers;
    private PartitionStrategy strategy;

    public Scheduler(int numServers, PartitionPolicy strategy) {
        servers = new ArrayList<>(numServers);

        for (int i = 0; i < numServers; i++) {
            var server = new Server();

            servers.add(server);

            var t = new Thread(server);
            t.run();
        }

        this.setStrategy(strategy);
    }

    public void setStrategy(PartitionPolicy strategy) {
        switch (strategy) {
            case SHORTEST_QUEUE -> this.strategy = new ShortestQueueStrategy();
            case SHORTEST_TIME -> this.strategy = new ShortestTimeStrategy();
        }
    }

    public void dispatchTask(Task task) {
        this.strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
