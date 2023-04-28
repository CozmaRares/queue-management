package com.raru.model.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.raru.model.data.Task;
import com.raru.model.data.Task.MutableTask;
import com.raru.utils.Global;

public class Server implements Runnable {
    private BlockingQueue<MutableTask> tasks;
    private int waitingTime;
    private AtomicBoolean running;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingTime = 0;
        running = new AtomicBoolean(false);
    }

    public void receiveTask(Task task) {
        tasks.add(task.toMutable());
        waitingTime += task.getServiceTime();
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(Global.getTimeUnitDuration());
    }

    @Override
    public void run() {
        running.set(true);

        try {
            while (running.get()) {
                var task = tasks.peek();

                if (task == null)
                    continue;

                sleep();
                task.decreaseServiceTime();
                waitingTime--;

                if (task.getServiceTime() == 0)
                    tasks.take();
            }
        } catch (InterruptedException e) {
        }
    }

    public void stop() {
        running.set(false);
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
