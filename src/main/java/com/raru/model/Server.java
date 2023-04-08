package com.raru.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingTime;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        tasks.add(task);
        waitingTime.addAndGet(task.getServiceTime());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = tasks.take();

                while (task.getServiceTime() > 0) {
                    Thread.sleep(1);
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
}
