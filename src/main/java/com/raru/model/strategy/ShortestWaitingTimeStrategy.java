package com.raru.model.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.data.Task;
import com.raru.utils.Logger;
import com.raru.utils.Logger.LogLevel;

public class ShortestWaitingTimeStrategy implements PartitionStrategy {
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

        if (idealServer != null) {
            Logger.logLine("Task " + task + " dispatched to server " + idealServer, LogLevel.TASK_PARTITION);
            idealServer.addTask(task);
        }
    }

}
