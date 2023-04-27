package com.raru.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

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
    Consumer<SimulationFrame> setFrame;

    public SimulationManager(
            int timeLimit,
            int minServingTime,
            int maxServingTime,
            int minArrivalTime,
            int maxArrivalTime,
            int numberOfTasks,
            int numberOfServers,
            PartitionPolicy policy,
            Consumer<SimulationFrame> setFrame) {

        this.setFrame = setFrame;
        this.timeLimit = timeLimit;
        this.scheduler = new Scheduler(numberOfServers, policy);

        this.tasks = SimulationManager.generateTasks(
                numberOfTasks,
                minServingTime,
                maxServingTime,
                minArrivalTime,
                maxArrivalTime);
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

        logStart();

        try {
            while (currentTime <= timeLimit) {
                var task = tasks.peek();

                while (task != null && task.getArrivalTime() <= currentTime) {
                    Logger.logLine("Dispatching task: " + task, LogLevel.TASK_PARTITION);

                    if (!scheduler.dispatchTask(task))
                        break;

                    tasks.remove();
                    task = tasks.peek();
                }

                var frame = scheduler.takeSnapshot(tasks, currentTime++);
                setFrame.accept(frame);

                if (frame.isEmpty())
                    break;

                Thread.sleep(Global.getTimeUnitDuration());
            }
        } catch (InterruptedException e) {
        }

        setFrame.accept(null);

        logFinish();
        scheduler.stop();
    }
}
