package com.raru.logic.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.Task;

public class ShortestTimeStrategy implements PartitionStrategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server idealServer = null;
        int minWaitingTime = Integer.MAX_VALUE;

        for (var server : servers) {
            int waitingTime = server.getWaitingTime();

            if (minWaitingTime > waitingTime) {
                minWaitingTime = waitingTime;
                idealServer = server;
            }
        }

        if (idealServer != null)
            idealServer.addTask(task);
    }

}
