package com.raru.model.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.SimulationManager;
import com.raru.model.data.Task;
import com.raru.utils.Logger;
import com.raru.utils.Logger.LogLevel;

public class ShortestWaitingTimeStrategy implements PartitionStrategy {
    @Override
    public boolean addTask(List<Server> servers, Task task) {
        final int MAX_QUEUE_SIZE = SimulationManager.getMaxQueueSize();

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

        if (idealServer == null) {
            Logger.logLine("No server available for task " + task, LogLevel.TASK_PARTITION);
            return false;
        }

        Logger.logLine("Task " + task + " dispatched to server " + idealServer, LogLevel.TASK_PARTITION);
        idealServer.addTask(task);

        return true;
    }

}
