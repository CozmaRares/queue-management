package com.raru.model.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.data.Task;

public interface PartitionStrategy {
    void addTask(List<Server> servers, Task task);
}
