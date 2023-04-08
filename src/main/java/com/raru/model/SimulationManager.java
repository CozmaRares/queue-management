package com.raru.model;

import java.util.ArrayList;
import java.util.List;

import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Random;

public class SimulationManager implements Runnable {
    private static int timeUnitDuration = 1000;

    private int timeLimit;
    private List<Task> tasks;
    private Scheduler scheduler;

    public SimulationManager(
            int timeLimit,
            int maxServingTime,
            int minServingTime,
            int maxArrivalTime,
            int minArrivalTime,
            int numberOfTasks,
            int numberOfServers,
            PartitionPolicy policy) {
        this.timeLimit = timeLimit;

        this.tasks = SimulationManager.generateTasks(
                numberOfTasks,
                maxServingTime,
                minServingTime,
                maxArrivalTime,
                minArrivalTime);

        this.scheduler = new Scheduler(numberOfServers, policy);
    }

    public static void setTimeUnitDuration(int durationMs) {
        SimulationManager.timeUnitDuration = durationMs;
    }

    public static int getTimeUnitDuration() {
        return SimulationManager.timeUnitDuration;
    }

    public static List<Task> generateTasks(
            int numberOfTasks,
            int maxServingTime,
            int minServingTime,
            int maxArrivalTime,
            int minArrivalTime) {

        var tasks = new ArrayList<Task>(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            int servingTime = Random.integer(minServingTime, maxServingTime + 1);
            int arrivalTime = Random.integer(minArrivalTime, maxArrivalTime + 1);

            tasks.add(new Task(arrivalTime, servingTime));
        }

        tasks.sort((a, b) -> a.getArrivalTime() - b.getArrivalTime());
        return tasks;
    }

    @Override
    public void run() {
        int currentTime = 0;

        while (currentTime < timeLimit) {
            for (var task : tasks) {
                if (task.getArrivalTime() != currentTime)
                    break;

                scheduler.dispatchTask(task);
            }

            final int ct = currentTime++;
            tasks.removeIf(t -> t.getArrivalTime() == ct);

            this.generateSnapShot();

            try {
                Thread.sleep(SimulationManager.timeUnitDuration);
            } catch (InterruptedException e) {
                this.scheduler.stop();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void generateSnapShot() {
        throw new UnsupportedOperationException("unimplemented 'generateSnapShot'");
    }
}
