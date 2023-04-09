package com.raru.controller;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.raru.model.data.SimulationFrame;

public class SimulationUpdater implements Runnable {
    private Supplier<Boolean> isApplicationRunning;
    private Supplier<Boolean> isFrameAvailable;
    private Supplier<SimulationFrame> getFrame;
    private Consumer<SimulationFrame> setFrame;

    public SimulationUpdater(
            Supplier<Boolean> isApplicationRunning,
            Supplier<Boolean> isFrameAvailable,
            Supplier<SimulationFrame> getFrame,
            Consumer<SimulationFrame> setFrame) {
        this.isApplicationRunning = isApplicationRunning;
        this.isFrameAvailable = isFrameAvailable;
        this.getFrame = getFrame;
        this.setFrame = setFrame;
    }

    @Override
    public void run() {
        while (isApplicationRunning.get()) {
            if (isFrameAvailable.get()) {
                var frame = getFrame.get();

                System.out.println("Time: " + frame.getSimulationTime());

                System.out.print("Tasks: ");

                for (var task : frame.getRemainingTasks())
                    System.out.print(task + " ");
                System.out.println();

                for (var q : frame.getQueues()) {
                    System.out.print("Q: ");

                    for (var task : q)
                        System.out.print(task + " ");

                    System.out.println();
                }

                System.out.println();
                System.out.println();
                System.out.println();

                setFrame.accept(frame);
            }
        }
    }

}
