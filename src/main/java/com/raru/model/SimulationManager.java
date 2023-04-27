package com.raru.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.raru.model.data.SimulationFrame;
import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Global;
import com.raru.utils.Logger;
import com.raru.utils.Util;
import com.raru.utils.Logger.LogLevel;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private Queue<Task> tasks;
    private Scheduler scheduler;
    private volatile SimulationFrame frame;
    private AtomicBoolean newFrameAvailable;
    private AtomicBoolean running;

    public SimulationManager(
            int timeLimit,
            int minServingTime,
            int maxServingTime,
            int minArrivalTime,
            int maxArrivalTime,
            int numberOfTasks,
            int numberOfServers,
            PartitionPolicy policy) {

        this.timeLimit = timeLimit;

        this.tasks = SimulationManager.generateTasks(
                numberOfTasks,
                minServingTime,
                maxServingTime,
                minArrivalTime,
                maxArrivalTime);

        this.scheduler = new Scheduler(numberOfServers, policy);

        this.newFrameAvailable = new AtomicBoolean(false);
        this.running = new AtomicBoolean(false);
    }

    public static Queue<Task> generateTasks(
            int numberOfTasks,
            int minServingTime,
            int maxServingTime,
            int minArrivalTime,
            int maxArrivalTime) {

        var tasks = new ArrayList<Task>(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            int servingTime = Util.randomInt(minServingTime, maxServingTime + 1);
            int arrivalTime = Util.randomInt(minArrivalTime, maxArrivalTime + 1);

            tasks.add(new Task(arrivalTime, servingTime));
        }

        tasks.sort((a, b) -> a.getArrivalTime() - b.getArrivalTime());

        return new LinkedList<>(tasks);
    }

    private void logStart() {
        Logger.logLine("SimulationManager started: " + this, LogLevel.THREAD_LIFETIME);
    }

    private void logFinish() {
        Logger.logLine("SimulationManager finished: " + this, LogLevel.THREAD_LIFETIME);
    }

    @Override
    public void run() {
        int currentTime = 0;

        running.set(true);
        logStart();

        while (currentTime <= timeLimit && running.get()) {
            var task = tasks.peek();

            while (task != null && task.getArrivalTime() <= currentTime) {
                Logger.logLine("Dispatching task: " + task, LogLevel.TASK_PARTITION);

                if (!scheduler.dispatchTask(task))
                    break;

                tasks.remove();
                task = tasks.peek();
            }

            frame = scheduler.takeSnapshot(tasks, currentTime++);
            newFrameAvailable.set(true);

            if (frame.isEmpty()) {
                running.set(false);
                break;
            }

            try {
                Thread.sleep(Global.getTimeUnitDuration());
            } catch (InterruptedException e) {
                logFinish();
                running.set(false);
                scheduler.stop();
                Thread.currentThread().interrupt();
            }
        }

        logFinish();
        running.set(false);
        scheduler.stop();
    }

    public void stop() {
        running.set(false);
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean isNewFrameAvailable() {
        return newFrameAvailable.get();
    }

    public SimulationFrame getSimulationFrame() {
        newFrameAvailable.set(false);

        return frame;
    }
}
