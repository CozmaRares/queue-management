package com.raru.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.raru.model.data.SimulationFrame;
import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Logger;
import com.raru.utils.Random;
import com.raru.utils.Logger.LogLevel;

public class SimulationManager implements Runnable {
    private static int timeUnitDuration = 1000;

    private int timeLimit;
    private List<Task> tasks;
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

    public static void setTimeUnitDuration(int durationMs) {
        SimulationManager.timeUnitDuration = durationMs;
    }

    public static int getTimeUnitDuration() {
        return SimulationManager.timeUnitDuration;
    }

    public static List<Task> generateTasks(
            int numberOfTasks,
            int minServingTime,
            int maxServingTime,
            int minArrivalTime,
            int maxArrivalTime) {

        var tasks = new ArrayList<Task>(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            int servingTime = Random.integer(minServingTime, maxServingTime + 1);
            int arrivalTime = Random.integer(minArrivalTime, maxArrivalTime + 1);

            tasks.add(new Task(arrivalTime, servingTime));
        }

        tasks.sort((a, b) -> a.getArrivalTime() - b.getArrivalTime());
        return tasks;
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
            for (var task : tasks) {
                if (task.getArrivalTime() != currentTime)
                    break;

                Logger.logLine("Dispatching task: " + task, LogLevel.TASK_PARTITION);
                scheduler.dispatchTask(task);
            }

            final int ct = currentTime++;
            tasks.removeIf(t -> t.getArrivalTime() == ct);

            frame = scheduler.takeSnapshot(tasks, ct);
            newFrameAvailable.set(true);

            try {
                Thread.sleep(SimulationManager.timeUnitDuration);
            } catch (InterruptedException e) {
                scheduler.stop();
                logFinish();
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
