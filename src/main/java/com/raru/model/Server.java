package com.raru.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.raru.model.data.Task;
import com.raru.model.data.Task.MutableTask;
import com.raru.utils.Global;

public class Server implements Runnable {
    private BlockingQueue<MutableTask> tasks;
    private AtomicInteger waitingTime;
    private AtomicBoolean running;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
        running = new AtomicBoolean(true);
    }

    public void addTask(Task task) {
        tasks.add(task.toMutable());
        waitingTime.addAndGet(task.getServiceTime());
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(Global.getTimeUnitDuration());
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                MutableTask task = tasks.peek();

                if (task == null)
                    continue;

                sleep();
                task.decreaseServiceTime();
                waitingTime.decrementAndGet();

                if (task.getServiceTime() == 0) {
                    tasks.take();
                }

            }
        } catch (InterruptedException e) {
        }
    }

    public void stop() {
        running.set(false);
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
