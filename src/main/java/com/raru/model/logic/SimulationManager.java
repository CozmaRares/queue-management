package com.raru.model.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import com.raru.model.data.SimulationFrame;
import com.raru.model.data.Task;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Global;
import com.raru.utils.Logger;
import com.raru.utils.Util;

public class SimulationManager implements Runnable {
    private final int timeLimit;
    private final List<Task> generatedTasks;
    private final Queue<Task> waitingTasks;
    private final Scheduler scheduler;
    private final int numberOfServers;
    private final Consumer<SimulationFrame> setFrame;

    private int totalWaitingTime;
    private int peakHour;
    private int peakHourTasks;
    private double averageServiceTime;

    public SimulationManager(
            int timeLimit,
            int minServiceTime,
            int maxServiceTime,
            int minArrivalTime,
            int maxArrivalTime,
            int numberOfTasks,
            int numberOfServers,
            PartitionPolicy policy,
            Consumer<SimulationFrame> setFrame) {

        this.setFrame = setFrame;
        this.timeLimit = timeLimit;
        this.scheduler = new Scheduler(numberOfServers, policy);
        this.waitingTasks = new LinkedList<>();
        this.peakHourTasks = 0;
        this.numberOfServers = numberOfServers;
        this.totalWaitingTime = 0;

        this.generatedTasks = SimulationManager.generateTasks(
                numberOfTasks,
                minServiceTime,
                maxServiceTime,
                minArrivalTime,
                maxArrivalTime);

        this.averageServiceTime = (double) this.generatedTasks
                .stream()
                .reduce(0, (sum, element) -> sum + element.getServiceTime(), Integer::sum);
        this.averageServiceTime /= numberOfTasks;

    }

    private static List<Task> generateTasks(
            int numberOfTasks,
            int minServiceTime,
            int maxServiceTime,
            int minArrivalTime,
            int maxArrivalTime) {

        var tasks = new ArrayList<Task>(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            int serviceTime = Util.randomInt(minServiceTime, maxServiceTime + 1);
            int arrivalTime = Util.randomInt(minArrivalTime, maxArrivalTime + 1);

            tasks.add(new Task(i + 1, arrivalTime, serviceTime));
        }

        tasks.sort((a, b) -> a.getArrivalTime() - b.getArrivalTime());

        return new ArrayList<>(tasks);
    }

    @Override
    public void run() {
        int currentTime = 0;

        try {
            while (currentTime <= timeLimit) {
                if (generatedTasks.size() > 0) {
                    for (var task : generatedTasks)
                        if (task.getArrivalTime() == currentTime)
                            waitingTasks.add(task);

                    final int ct = currentTime;
                    generatedTasks.removeIf(t -> t.getArrivalTime() == ct);
                }

                var task = waitingTasks.peek();

                while (task != null && task.getArrivalTime() <= currentTime) {
                    if (!scheduler.dispatchTask(task))
                        break;

                    waitingTasks.remove();
                    task = waitingTasks.peek();
                }

                var frame = scheduler.takeSnapshot(generatedTasks, waitingTasks, currentTime);
                setFrame.accept(frame);

                if (frame.isEmpty() && generatedTasks.size() == 0)
                    break;

                int queuedTasks = frame
                        .getQueues()
                        .stream()
                        .reduce(0, (sum, q) -> sum + q.first.size(), Integer::sum);
                queuedTasks += frame.getWaitingTasks().size();

                totalWaitingTime += frame
                        .getQueues()
                        .stream()
                        .reduce(0, (sum, q) -> sum + q.second, Integer::sum);

                if (peakHourTasks < queuedTasks) {
                    peakHourTasks = queuedTasks;
                    peakHour = currentTime;
                }

                Thread.sleep(Global.getTimeUnitDuration());

                currentTime++;
            }

            double averageWaitingTime = (double) totalWaitingTime / numberOfServers / currentTime;
            Logger.logLine(String.format("Average waiting time: %.3f", averageWaitingTime));
            Logger.logLine(String.format("Average service time: %.3f", averageServiceTime));
            Logger.logLine(String.format("Peak hour: %d", peakHour));

            setFrame.accept(null);
        } catch (InterruptedException e) {
            Logger.logLine("Simulation stopped early");
        }

        scheduler.stop();
    }
}
