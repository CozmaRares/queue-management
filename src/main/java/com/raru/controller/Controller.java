package com.raru.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.raru.model.SimulationManager;
import com.raru.model.strategy.PartitionPolicy;
import com.raru.view.SetupView;
import com.raru.view.SimulationView;

public class Controller {
    private SetupView setupView;
    private SimulationView simulationView;
    private SimulationManager manager;
    private Thread managerThread;
    private SimulationUpdater updater;

    public Controller() {
        setupView = new SetupView();

        setupView.setVisible(true);
        setupView.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int timeLimit = Integer.parseInt(setupView.getTimeLimit());
                int minServingTime = Integer.parseInt(setupView.getMinServingTime());
                int maxServingTime = Integer.parseInt(setupView.getMaxServingTime());
                int minArrivalTime = Integer.parseInt(setupView.getMinArrivalTime());
                int maxArrivalTime = Integer.parseInt(setupView.getMaxArrivalTime());
                int numberOfTasks = Integer.parseInt(setupView.getNumberOfTasks());
                int numberOfServers = Integer.parseInt(setupView.getNumberOfServers());
                PartitionPolicy policy = PartitionPolicy.values()[setupView.getSelectedPolicyIndex()];

                manager = new SimulationManager(
                        timeLimit,
                        minServingTime,
                        maxServingTime,
                        minArrivalTime,
                        maxArrivalTime,
                        numberOfTasks,
                        numberOfServers,
                        policy);

                managerThread = new Thread(manager);
                managerThread.start();

                updater = new SimulationUpdater(
                        manager::isRunning,
                        manager::isNewFrameAvailable,
                        manager::getSimulationFrame,
                        simulationView::setFrame);
                new Thread(updater).start();

                setupView.setVisible(false);
                simulationView.setVisible(true);
            }
        });

        simulationView = new SimulationView();

        simulationView.setStopButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                managerThread.interrupt();

                setupView.setVisible(true);
                simulationView.setVisible(false);
            }
        });
    }

}
