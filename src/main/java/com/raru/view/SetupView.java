package com.raru.view;

import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.raru.model.strategy.PartitionPolicy;
import com.raru.utils.NumberField;
import com.raru.utils.Logger.LogLevel;

public class SetupView extends JFrame {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;

    private static final int ROW_GAP = 10;
    private static final int COL_GAP = 10;

    private static final int COL_WIDTH = 200;
    private static final int ROW_HEIGHT = 30;

    private static final int WINDOW_WIDTH = MARGIN_X * 2 + COL_GAP + COL_WIDTH * 2;
    private static final int WINDOW_HEIGHT = row(12) + ROW_HEIGHT + MARGIN_Y;

    NumberField timeLimitField;
    NumberField maxServingTimeField;
    NumberField minServingTimeField;
    NumberField maxArrivalTimeField;
    NumberField minArrivalTimeField;
    NumberField numberOfTasksField;
    NumberField numberOfServersField;
    Choice policyChoice;
    Choice logChoice;
    JTextField pathField;
    JButton startButton;
    NumberField durationField;

    public SetupView() {
        var timeLimitLabel = new JLabel("Time limit:");
        timeLimitLabel.setBounds(col(0), row(0), COL_WIDTH, ROW_HEIGHT);

        var minServingTimeLabel = new JLabel("Min serving time:");
        minServingTimeLabel.setBounds(col(0), row(1), COL_WIDTH, ROW_HEIGHT);

        var maxServingTimeLabel = new JLabel("Max serving time:");
        maxServingTimeLabel.setBounds(col(0), row(2), COL_WIDTH, ROW_HEIGHT);

        var minArrivalTimeLabel = new JLabel("Min arrival time:");
        minArrivalTimeLabel.setBounds(col(0), row(3), COL_WIDTH, ROW_HEIGHT);

        var maxArrivalTimeLabel = new JLabel("Max arrival time:");
        maxArrivalTimeLabel.setBounds(col(0), row(4), COL_WIDTH, ROW_HEIGHT);

        var numberOfTasksLabel = new JLabel("Number of tasks:");
        numberOfTasksLabel.setBounds(col(0), row(5), COL_WIDTH, ROW_HEIGHT);

        var numberOfServersLabel = new JLabel("Number of servers:");
        numberOfServersLabel.setBounds(col(0), row(6), COL_WIDTH, ROW_HEIGHT);

        var policyLabel = new JLabel("Policy:");
        policyLabel.setBounds(col(0), row(7), COL_WIDTH, ROW_HEIGHT);

        var logLabel = new JLabel("Log level:");
        logLabel.setBounds(col(0), row(8), COL_WIDTH, ROW_HEIGHT);

        var pathLabel = new JLabel("Name of log file:");
        pathLabel.setBounds(col(0), row(9), COL_WIDTH, ROW_HEIGHT);

        var durationLabel = new JLabel("Time unit duration (ms):");
        durationLabel.setBounds(col(0), row(10), COL_WIDTH, ROW_HEIGHT);

        timeLimitField = new NumberField(60);
        timeLimitField.setBounds(col(1), row(0), COL_WIDTH, ROW_HEIGHT);

        minServingTimeField = new NumberField(2);
        minServingTimeField.setBounds(col(1), row(1), COL_WIDTH, ROW_HEIGHT);

        maxServingTimeField = new NumberField(30);
        maxServingTimeField.setBounds(col(1), row(2), COL_WIDTH, ROW_HEIGHT);

        minArrivalTimeField = new NumberField(2);
        minArrivalTimeField.setBounds(col(1), row(3), COL_WIDTH, ROW_HEIGHT);

        maxArrivalTimeField = new NumberField(4);
        maxArrivalTimeField.setBounds(col(1), row(4), COL_WIDTH, ROW_HEIGHT);

        numberOfTasksField = new NumberField(4);
        numberOfTasksField.setBounds(col(1), row(5), COL_WIDTH, ROW_HEIGHT);

        numberOfServersField = new NumberField(2);
        numberOfServersField.setBounds(col(1), row(6), COL_WIDTH, ROW_HEIGHT);

        policyChoice = new Choice();
        policyChoice.setBounds(col(1), row(7), COL_WIDTH, ROW_HEIGHT);
        policyChoice.setBackground(Color.WHITE);

        for (var policy : PartitionPolicy.values())
            policyChoice.add(policy.name().toLowerCase().replace("_", " "));

        logChoice = new Choice();
        logChoice.setBounds(col(1), row(8), COL_WIDTH, ROW_HEIGHT);
        logChoice.setBackground(Color.WHITE);

        for (var level : LogLevel.values())
            logChoice.add(level.name().toLowerCase().replace("_", " "));

        pathField = new JTextField("log");
        pathField.setBounds(col(1), row(9), COL_WIDTH, ROW_HEIGHT);

        durationField = new NumberField(1000);
        durationField.setBounds(col(1), row(10), COL_WIDTH, ROW_HEIGHT);

        startButton = new JButton("Start Simulation");
        startButton.setBounds((WINDOW_WIDTH - COL_WIDTH) / 2, row(11), COL_WIDTH, ROW_HEIGHT);

        add(timeLimitLabel);
        add(timeLimitField);

        add(minServingTimeLabel);
        add(minServingTimeField);

        add(maxServingTimeLabel);
        add(maxServingTimeField);

        add(minArrivalTimeLabel);
        add(minArrivalTimeField);

        add(maxArrivalTimeLabel);
        add(maxArrivalTimeField);

        add(numberOfServersLabel);
        add(numberOfServersField);

        add(numberOfTasksLabel);
        add(numberOfTasksField);

        add(policyLabel);
        add(policyChoice);

        add(logLabel);
        add(logChoice);

        add(pathLabel);
        add(pathField);

        add(durationLabel);
        add(durationField);

        add(startButton);

        setLayout(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static int row(int rowNumber) {
        return MARGIN_Y + rowNumber * (ROW_HEIGHT + ROW_GAP);
    }

    private static int col(int colNumber) {
        int column = 0;

        if (colNumber == 1)
            column = COL_WIDTH + COL_GAP;

        if (colNumber > 1)
            column = COL_WIDTH + COL_GAP + (colNumber - 1) * (COL_WIDTH + COL_GAP);

        return MARGIN_X + column;
    }

    public String getTimeLimit() {
        return timeLimitField.getText();
    }

    public String getMinServingTime() {
        return minServingTimeField.getText();
    }

    public String getMaxServingTime() {
        return maxServingTimeField.getText();
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

    public int getSelectedLogLevelIndex() {
        return logChoice.getSelectedIndex();
    }

    public String getLogFileName() {
        return pathField.getText();
    }

    public String getTimeUnitDuration() {
        return durationField.getText();
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}
