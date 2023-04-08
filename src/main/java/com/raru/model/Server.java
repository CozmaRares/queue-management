package com.raru.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.raru.logic.SimulationManager.getTimeUnitDuration;

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

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void stop() {
        running.set(false);
    }
}
