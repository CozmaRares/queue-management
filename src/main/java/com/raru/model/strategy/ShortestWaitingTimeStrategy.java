package com.raru.model.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.data.Task;
import com.raru.utils.Global;

public class ShortestWaitingTimeStrategy implements PartitionStrategy {
    @Override
    public boolean addTask(List<Server> servers, Task task) {
        final int MAX_QUEUE_SIZE = Global.getMaxQueueSize();

        Server idealServer = null;
        int minWaitingTime = Integer.MAX_VALUE;

        for (var server : servers) {
            int waitingTime = server.getWaitingTime();

            if (server.getNumberOfTasks() >= MAX_QUEUE_SIZE)
                continue;

            if (minWaitingTime > waitingTime) {
                minWaitingTime = waitingTime;
                idealServer = server;
            }
        }

        if (idealServer == null)
            return false;

        idealServer.addTask(task);

        return true;
    }

}
