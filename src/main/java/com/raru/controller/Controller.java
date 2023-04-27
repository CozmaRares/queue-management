package com.raru.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.raru.model.SimulationManager;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.Global;
import com.raru.utils.Logger;
import com.raru.view.SetupView;
import com.raru.view.SimulationView;

public class Controller {
    private SetupView setupView;
    private SimulationView simulationView;
    private SimulationManager manager;
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
                int minServingTime = Integer.parseInt(setupView.getMinServingTime());
                int maxServingTime = Integer.parseInt(setupView.getMaxServingTime());
                int minArrivalTime = Integer.parseInt(setupView.getMinArrivalTime());
                int maxArrivalTime = Integer.parseInt(setupView.getMaxArrivalTime());
                int numberOfServers = Integer.parseInt(setupView.getNumberOfServers());
                int timeUnitDuration = Integer.parseInt(setupView.getTimeUnitDuration());

                String logFile = setupView.getLogFileName() + ".log";
                PartitionPolicy policy = PartitionPolicy.values()[setupView.getSelectedPolicyIndex()];

                Logger.openFile(logFile);

                Global.setTimeUnitDuration(timeUnitDuration);
                Global.setMaxQueueSize(maxQueueSize);

                manager = new SimulationManager(
                        timeLimit,
                        minServingTime,
                        maxServingTime,
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
