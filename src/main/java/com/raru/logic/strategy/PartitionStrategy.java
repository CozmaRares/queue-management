package com.raru.logic.strategy;

import java.util.List;

import com.raru.model.Server;
import com.raru.model.Task;

public interface PartitionStrategy {
    void addTask(List<Server> servers, Task task);
}
