package com.raru.view;

import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.NumberField;
import com.raru.utils.Util;

public class SetupView extends JFrame {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;

    private static final int ROW_GAP = 10;
    private static final int COL_GAP = 10;

    private static final int COL_WIDTH = 200;
    private static final int ROW_HEIGHT = 30;

    private static final int LABEL_COL = col(0);
    private static final int SETTING_COL = col(1);

    private static final int TIME_LIMIT_ROW = row(0);
    private static final int MIN_SERVICE_TIME_ROW = row(1);
    private static final int MAX_SERVICE_TIME_ROW = row(2);
    private static final int MIN_ARRIVAL_TIME_ROW = row(3);
    private static final int MAX_ARRIVAL_TIME_ROW = row(4);
    private static final int NUM_TASKS_ROW = row(5);
    private static final int NUM_SERVERS_ROW = row(6);
    private static final int POLICY_ROW = row(7);
    private static final int PATH_ROW = row(8);
    private static final int DURATION_ROW = row(9);
    private static final int MAX_Q_SIZE_ROW = row(10);
    private static final int START_ROW = row(11);

    private static final int WINDOW_WIDTH = MARGIN_X * 2 + COL_GAP + COL_WIDTH * 2;
    private static final int WINDOW_HEIGHT = row(12) + ROW_HEIGHT + MARGIN_Y;

    private NumberField timeLimitField;
    private NumberField minServiceTimeField;
    private NumberField maxServiceTimeField;
    private NumberField minArrivalTimeField;
    private NumberField maxArrivalTimeField;
    private NumberField numberOfTasksField;
    private NumberField numberOfServersField;
    private Choice policyChoice;
    private JTextField pathField;
    private JButton startButton;
    private NumberField durationField;
    private NumberField maxQueueSize;

    public SetupView() {
        var timeLimitLabel = new JLabel("Time limit:");
        timeLimitField = new NumberField(60);
        timeLimitLabel.setBounds(LABEL_COL, TIME_LIMIT_ROW, COL_WIDTH, ROW_HEIGHT);
        timeLimitField.setBounds(SETTING_COL, TIME_LIMIT_ROW, COL_WIDTH, ROW_HEIGHT);
        add(timeLimitLabel);
        add(timeLimitField);

        var minServiceTimeLabel = new JLabel("Min service time:");
        minServiceTimeField = new NumberField(2);
        minServiceTimeLabel.setBounds(LABEL_COL, MIN_SERVICE_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        minServiceTimeField.setBounds(SETTING_COL, MIN_SERVICE_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        add(minServiceTimeLabel);
        add(minServiceTimeField);

        var maxServiceTimeLabel = new JLabel("Max service time:");
        maxServiceTimeField = new NumberField(30);
        maxServiceTimeLabel.setBounds(LABEL_COL, MAX_SERVICE_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        maxServiceTimeField.setBounds(SETTING_COL, MAX_SERVICE_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        add(maxServiceTimeLabel);
        add(maxServiceTimeField);

        var minArrivalTimeLabel = new JLabel("Min arrival time:");
        minArrivalTimeField = new NumberField(2);
        minArrivalTimeLabel.setBounds(LABEL_COL, MIN_ARRIVAL_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        minArrivalTimeField.setBounds(SETTING_COL, MIN_ARRIVAL_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        add(minArrivalTimeLabel);
        add(minArrivalTimeField);

        var maxArrivalTimeLabel = new JLabel("Max arrival time:");
        maxArrivalTimeField = new NumberField(4);
        maxArrivalTimeLabel.setBounds(LABEL_COL, MAX_ARRIVAL_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        maxArrivalTimeField.setBounds(SETTING_COL, MAX_ARRIVAL_TIME_ROW, COL_WIDTH, ROW_HEIGHT);
        add(maxArrivalTimeLabel);
        add(maxArrivalTimeField);

        var numberOfTasksLabel = new JLabel("Number of tasks:");
        numberOfTasksField = new NumberField(4);
        numberOfTasksLabel.setBounds(LABEL_COL, NUM_TASKS_ROW, COL_WIDTH, ROW_HEIGHT);
        numberOfTasksField.setBounds(SETTING_COL, NUM_TASKS_ROW, COL_WIDTH, ROW_HEIGHT);
        add(numberOfTasksLabel);
        add(numberOfTasksField);

        var numberOfServersLabel = new JLabel("Number of servers:");
        numberOfServersField = new NumberField(2);
        numberOfServersLabel.setBounds(LABEL_COL, NUM_SERVERS_ROW, COL_WIDTH, ROW_HEIGHT);
        numberOfServersField.setBounds(SETTING_COL, NUM_SERVERS_ROW, COL_WIDTH, ROW_HEIGHT);
        add(numberOfServersLabel);
        add(numberOfServersField);

        var policyLabel = new JLabel("Policy:");
        policyChoice = new Choice();
        policyLabel.setBounds(LABEL_COL, POLICY_ROW, COL_WIDTH, ROW_HEIGHT);
        policyChoice.setBounds(SETTING_COL, POLICY_ROW, COL_WIDTH, ROW_HEIGHT);
        add(policyLabel);
        add(policyChoice);

        policyChoice.setBackground(Color.WHITE);
        for (var policy : PartitionPolicy.values())
            policyChoice.add(Util.formatWords(policy.name()));

        var pathLabel = new JLabel("Name of log file:");
        pathField = new JTextField("log.log");
        pathLabel.setBounds(LABEL_COL, PATH_ROW, COL_WIDTH, ROW_HEIGHT);
        pathField.setBounds(SETTING_COL, PATH_ROW, COL_WIDTH, ROW_HEIGHT);
        add(pathLabel);
        add(pathField);

        var durationLabel = new JLabel("Time unit duration (ms):");
        durationField = new NumberField(1000);
        durationLabel.setBounds(LABEL_COL, DURATION_ROW, COL_WIDTH, ROW_HEIGHT);
        durationField.setBounds(SETTING_COL, DURATION_ROW, COL_WIDTH, ROW_HEIGHT);
        add(durationLabel);
        add(durationField);

        var maxSizeLabel = new JLabel("Max queue size:");
        maxQueueSize = new NumberField(-1);
        maxSizeLabel.setBounds(LABEL_COL, MAX_Q_SIZE_ROW, COL_WIDTH, ROW_HEIGHT);
        maxQueueSize.setBounds(SETTING_COL, MAX_Q_SIZE_ROW, COL_WIDTH, ROW_HEIGHT);
        add(maxSizeLabel);
        add(maxQueueSize);

        startButton = new JButton("Start Simulation");
        startButton.setBounds((WINDOW_WIDTH - COL_WIDTH) / 2, START_ROW, COL_WIDTH, ROW_HEIGHT);
        add(startButton);

        setLayout(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static int row(int rowNumber) {
        return MARGIN_Y + rowNumber * (ROW_HEIGHT + ROW_GAP);
    }

    private static int col(int colNumber) {
        return MARGIN_X + colNumber * (COL_WIDTH + COL_GAP);
    }

    public String getTimeLimit() {
        return timeLimitField.getText();
    }

    public String getMinServiceTime() {
        return minServiceTimeField.getText();
    }

    public String getMaxServiceTime() {
        return maxServiceTimeField.getText();
    }

    public String getMinArrivalTime() {
        return minArrivalTimeField.getText();
    }

    public String getMaxArrivalTime() {
        return maxArrivalTimeField.getText();
    }

    public String getNumberOfTasks() {
        return numberOfTasksField.getText();
    }

    public String getNumberOfServers() {
        return numberOfServersField.getText();
    }

    public int getSelectedPolicyIndex() {
        return policyChoice.getSelectedIndex();
    }

    public String getLogFileName() {
        return pathField.getText();
    }

    public String getTimeUnitDuration() {
        return durationField.getText();
    }

    public String getMaxQueueSize() {
        return maxQueueSize.getText();
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}
