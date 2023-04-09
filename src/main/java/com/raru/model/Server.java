package com.raru.model;

import static com.raru.model.SimulationManager.getTimeUnitDuration;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.raru.model.data.Task.MutableTask;
import com.raru.model.data.Task;
import com.raru.model.data.Task.ImmutableTask;

public class Server implements Runnable {
    private BlockingQueue<MutableTask> tasks;
    private AtomicInteger waitingTime;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        tasks.add(task.toMutable());
        waitingTime.addAndGet(task.getServiceTime());
    }

    @Override
    public void run() {
        try {
            while (true) {
                MutableTask task = tasks.peek();

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

    public List<ImmutableTask> getTasks() {
        return tasks
                .stream()
                .map(task -> task.toImmutable())
                .collect(Collectors.toList());
    }

}
