package com.raru.model.strategy;

import java.util.List;

import com.raru.model.data.Task;
import com.raru.model.logic.Server;

public interface PartitionStrategy {
    boolean addTask(List<Server> servers, Task task);
}
