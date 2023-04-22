package com.raru.controller;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.raru.model.data.SimulationFrame;
import com.raru.utils.Logger;
import com.raru.utils.Logger.LogLevel;

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

    private void logStart() {
        Logger.logLine("SimulationUpdater started: " + this, LogLevel.THREAD_LIFETIME);
    }

    private void logFinish() {
        Logger.logLine("SimulationUpdater finished: " + this, LogLevel.THREAD_LIFETIME);
    }

    @Override
    public void run() {
        this.logStart();

        while (isApplicationRunning.get()) {
            if (!isFrameAvailable.get())
                continue;

            setFrame.accept(getFrame.get());
        }

        this.logFinish();
    }

}
