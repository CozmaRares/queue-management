package com.raru.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.raru.model.logic.SimulationManager;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Global;
import com.raru.utils.Logger;
import com.raru.view.SetupView;
import com.raru.view.SimulationView;

public class Controller {
    private SetupView setupView;
    private SimulationView simulationView;
    private Thread managerThread;

    public Controller() {
        setupView = new SetupView();

        setupView.setVisible(true);
        setupView.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int timeLimit = Integer.parseInt(setupView.getTimeLimit());
                int maxQueueSize = Integer.parseInt(setupView.getMaxQueueSize());
                int numberOfTasks = Integer.parseInt(setupView.getNumberOfTasks());
                int minServiceTime = Integer.parseInt(setupView.getMinServiceTime());
                int maxServiceTime = Integer.parseInt(setupView.getMaxServiceTime());
                int minArrivalTime = Integer.parseInt(setupView.getMinArrivalTime());
                int maxArrivalTime = Integer.parseInt(setupView.getMaxArrivalTime());
                int numberOfServers = Integer.parseInt(setupView.getNumberOfServers());
                int timeUnitDuration = Integer.parseInt(setupView.getTimeUnitDuration());

                String logFile = setupView.getLogFileName();
                PartitionPolicy policy = PartitionPolicy.values()[setupView.getSelectedPolicyIndex()];

                Global.setTimeUnitDuration(timeUnitDuration);
                Global.setMaxQueueSize(maxQueueSize);

                Logger.openFile(logFile);

                Logger.logLine("Simulation settings");
                Logger.logLine("Time limit: " + timeLimit);
                Logger.logLine("Min service time: " + minServiceTime);
                Logger.logLine("Max service time: " + maxServiceTime);
                Logger.logLine("Min arrival time: " + minArrivalTime);
                Logger.logLine("Max arrival time: " + maxArrivalTime);
                Logger.logLine("Number of tasks: " + numberOfTasks);
                Logger.logLine("Number of servers: " + numberOfServers);
                Logger.logLine("Partition policy: " + policy.name());
                Logger.logLine("Max queue size: " + maxQueueSize);
                Logger.log("\n\n\n");

                var manager = new SimulationManager(
                        timeLimit,
                        minServiceTime,
                        maxServiceTime,
                        minArrivalTime,
                        maxArrivalTime,
                        numberOfTasks,
                        numberOfServers,
                        policy,
                        simulationView::setFrame);

                managerThread = new Thread(manager);
                managerThread.start();

                setupView.setVisible(false);
                simulationView.setVisible(true);
            }
        });

        simulationView = new SimulationView();

        simulationView.setStopButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    managerThread.interrupt();
                    managerThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Logger.closeFile();
                setupView.setVisible(true);
                simulationView.setVisible(false);
            }
        });
    }

}
