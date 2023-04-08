package com.raru.view;

import java.awt.Choice;
import java.awt.Color;

import javax.swing.*;

import com.raru.logic.strategy.PartitionPolicy;
import com.raru.utils.NumberField;

public class SetupView extends JFrame {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;

    private static final int ROW_GAP = 10;
    private static final int COL_GAP = 10;

    private static final int COL0_WIDTH = 150;
    private static final int COL1_WIDTH = 200;
    private static final int ROW_HEIGHT = 30;

    private static final int WINDOW_WIDTH = MARGIN_X * 2 + COL_GAP + COL0_WIDTH + COL1_WIDTH;
    private static final int WINDOW_HEIGHT = row(9) + ROW_HEIGHT + MARGIN_Y;

    NumberField timeLimitField;
    NumberField maxServingTimeField;
    NumberField minServingTimeField;
    NumberField maxArrivalTimeField;
    NumberField minArrivalTimeField;
    NumberField numberOfTasksField;
    NumberField numberOfServersField;
    Choice policyChoice;
    JButton startButton;

    public SetupView() {
        var timeLimitLabel = new JLabel("Time limit:");
        timeLimitLabel.setBounds(column(0), row(0), COL0_WIDTH, ROW_HEIGHT);

        var maxServingTimeLabel = new JLabel("Max serving time:");
        maxServingTimeLabel.setBounds(column(0), row(1), COL0_WIDTH, ROW_HEIGHT);

        var minServingTimeLabel = new JLabel("Min serving time:");
        minServingTimeLabel.setBounds(column(0), row(2), COL0_WIDTH, ROW_HEIGHT);

        var maxArrivalTimeLabel = new JLabel("Max arrival time:");
        maxArrivalTimeLabel.setBounds(column(0), row(3), COL0_WIDTH, ROW_HEIGHT);

        var minArrivalTimeLabel = new JLabel("Min arrival time:");
        minArrivalTimeLabel.setBounds(column(0), row(4), COL0_WIDTH, ROW_HEIGHT);

        var numberOfTasksLabel = new JLabel("Number of tasks:");
        numberOfTasksLabel.setBounds(column(0), row(5), COL0_WIDTH, ROW_HEIGHT);

        var numberOfServersLabel = new JLabel("Number of servers:");
        numberOfServersLabel.setBounds(column(0), row(6), COL0_WIDTH, ROW_HEIGHT);

        var policyLabel = new JLabel("Policy:");
        policyLabel.setBounds(column(0), row(7), COL0_WIDTH, ROW_HEIGHT);

        timeLimitField = new NumberField();
        timeLimitField.setBounds(column(1), row(0), COL1_WIDTH, ROW_HEIGHT);

        maxServingTimeField = new NumberField();
        maxServingTimeField.setBounds(column(1), row(1), COL1_WIDTH, ROW_HEIGHT);

        minServingTimeField = new NumberField();
        minServingTimeField.setBounds(column(1), row(2), COL1_WIDTH, ROW_HEIGHT);

        maxArrivalTimeField = new NumberField();
        maxArrivalTimeField.setBounds(column(1), row(3), COL1_WIDTH, ROW_HEIGHT);

        minArrivalTimeField = new NumberField();
        minArrivalTimeField.setBounds(column(1), row(4), COL1_WIDTH, ROW_HEIGHT);

        numberOfTasksField = new NumberField();
        numberOfTasksField.setBounds(column(1), row(5), COL1_WIDTH, ROW_HEIGHT);

        numberOfServersField = new NumberField();
        numberOfServersField.setBounds(column(1), row(6), COL1_WIDTH, ROW_HEIGHT);

        policyChoice = new Choice();
        policyChoice.setBounds(column(1), row(7), COL1_WIDTH, ROW_HEIGHT);
        policyChoice.setBackground(Color.WHITE);

        for (var policy : PartitionPolicy.values())
            policyChoice.add(policy.name().toLowerCase().replace("_", " "));

        startButton = new JButton("Start Simulation");
        startButton.setBounds((WINDOW_WIDTH - COL1_WIDTH) / 2, row(8), COL1_WIDTH, ROW_HEIGHT);

        add(timeLimitLabel);
        add(timeLimitField);

        add(maxServingTimeLabel);
        add(maxServingTimeField);

        add(minServingTimeLabel);
        add(minServingTimeField);

        add(maxArrivalTimeLabel);
        add(maxArrivalTimeField);

        add(minArrivalTimeLabel);
        add(minArrivalTimeField);

        add(numberOfServersLabel);
        add(numberOfServersField);

        add(numberOfTasksLabel);
        add(numberOfTasksField);

        add(policyLabel);
        add(policyChoice);

        add(startButton);

        setLayout(null);
        setVisible(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static int row(int rowNumber) {
        return MARGIN_Y + rowNumber * (ROW_HEIGHT + ROW_GAP);
    }

    private static int column(int colNumber) {
        int col = 0;

        if (colNumber == 1)
            col = COL0_WIDTH + COL_GAP;

        if (colNumber > 1)
            col = COL0_WIDTH + COL_GAP + (colNumber - 1) * (COL1_WIDTH + COL_GAP);

        return MARGIN_X + col;
    }

}
