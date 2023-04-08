package com.raru.model.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.data.Task;

public class ShortestQueueStrategy implements PartitionStrategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server idealServer = null;
        int minSize = Integer.MAX_VALUE;

        for (var server : servers) {
            int size = server.getNumberOfTasks();

            if (minSize > size) {
                minSize = size;
                idealServer = server;
            }
        }

        if (idealServer != null)
            idealServer.addTask(task);
    }
}
