package com.raru.model.strategy;

import java.util.List;

import com.raru.model.data.Task;
import com.raru.model.logic.Server;
import com.raru.utils.Global;

public class ShortestQueueStrategy implements PartitionStrategy {
    @Override
    public boolean addTask(List<Server> servers, Task task) {
        final int MAX_QUEUE_SIZE = Global.getMaxQueueSize();

        Server idealServer = null;
        int minSize = Integer.MAX_VALUE;

        for (var server : servers) {
            int size = server.getNumberOfTasks();

            if (size >= MAX_QUEUE_SIZE)
                continue;

            if (minSize > size) {
                minSize = size;
                idealServer = server;
            }
        }

        if (idealServer == null)
            return false;

        idealServer.receiveTask(task);

        return true;
    }
}
