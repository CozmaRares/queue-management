package com.raru.model;

import static com.raru.model.SimulationManager.getTimeUnitDuration;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.raru.model.data.Task;
import com.raru.model.data.Task.ReadOnlyTask;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingTime;
    private AtomicBoolean running;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
        running = new AtomicBoolean(true);
    }

    public void addTask(Task task) {
        tasks.add(task);
        waitingTime.addAndGet(task.getServiceTime());
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                Task task = tasks.take();

                while (task.getServiceTime() > 0) {
                    Thread.sleep(getTimeUnitDuration());
                    task.decreaseServiceTime();
                    waitingTime.decrementAndGet();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getWaitingTime() {
        return waitingTime.get();
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    public List<ReadOnlyTask> getTasks() {
        return tasks
                .stream()
                .map(task -> task.readonly())
                .collect(Collectors.toList());
    }

    public void stop() {
        running.set(false);
    }
}
