package com.raru.view;

import java.awt.event.ActionListener;

import javax.swing.*;

import com.raru.model.data.SimulationFrame;
import com.raru.utils.Logger;

public class SimulationView extends JFrame {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;

    private static final int ROW_GAP = 10;
    private static final int COL_GAP = 10;

    private static final int COL_WIDTH = 150;
    private static final int ROW_HEIGHT = 30;

    // private static final int WINDOW_WIDTH = MARGIN_X * 2 + COL_GAP + COL0_WIDTH +
    // COL1_WIDTH;
    // private static final int WINDOW_HEIGHT = row(9) + ROW_HEIGHT + MARGIN_Y;

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

    private JButton stopButton;

    public SimulationView() {
        setLayout(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        stopButton = new JButton("Stop Simulation");
    }

    private static int row(int rowNumber) {
        return MARGIN_Y + rowNumber * (ROW_HEIGHT + ROW_GAP);
    }

    private static int col(int colNumber) {
        return MARGIN_X + colNumber * (COL_WIDTH + COL_GAP);
    }

    public void setFrame(SimulationFrame frame) {
        getContentPane().removeAll();

        if (frame == null)
            updateFrameFinished();
        else
            updateFrame(frame);

        validate();
        repaint();
    }

    private void updateFrameFinished() {
        Logger.logLine("Finished");

        var l = new JLabel("Finished");
        l.setBounds((WINDOW_WIDTH - COL_WIDTH) / 2, row(1), COL_WIDTH, ROW_HEIGHT);
        add(l);

        stopButton.setText("Return to Setup");
        stopButton.setBounds((WINDOW_WIDTH - COL_WIDTH) / 2, row(2), COL_WIDTH, ROW_HEIGHT);
        add(stopButton);
    }

    private void updateFrame(SimulationFrame frame) {
        Logger.logLine("Time: " + frame.getSimulationTime());

        var l = new JLabel("Time: " + frame.getSimulationTime());
        l.setBounds(col(0), row(0), COL_WIDTH, ROW_HEIGHT);
        add(l);

        var remainingTasks = frame.getRemainingTasks();

        Logger.log("Remaining tasks: ");

        l = new JLabel("Remaining tasks");
        l.setBounds(col(0), row(1), COL_WIDTH, ROW_HEIGHT);
        add(l);

        if (remainingTasks.size() == 0) {
            Logger.log("None");
            l = new JLabel("None");
            l.setBounds(col(1), row(1), COL_WIDTH, ROW_HEIGHT);
            add(l);
        } else
            for (int i = 0; i < remainingTasks.size(); i++) {
                var task = remainingTasks.get(i);

                Logger.log(task + " ");
                l = new JLabel(task.toString());
                l.setBounds(col(i + 1), row(1), COL_WIDTH, ROW_HEIGHT);
                add(l);
            }

        var waitingTasks = frame.getWaitingTasks();

        Logger.log("\nWaiting tasks: ");

        l = new JLabel("Waiting tasks");
        l.setBounds(col(0), row(2), COL_WIDTH, ROW_HEIGHT);
        add(l);

        if (waitingTasks.size() == 0) {
            Logger.log("None");
            l = new JLabel("None");
            l.setBounds(col(1), row(2), COL_WIDTH, ROW_HEIGHT);
            add(l);
        } else
            for (int i = 0; i < waitingTasks.size(); i++) {
                var task = waitingTasks.get(i);

                Logger.log(task + " ");
                l = new JLabel(task.toString());
                l.setBounds(col(i + 1), row(2), COL_WIDTH, ROW_HEIGHT);
                add(l);
            }

        var queues = frame.getQueues();

        for (int i = 0; i < queues.size(); i++) {
            Logger.log("\nQueue " + i + ": ");
            l = new JLabel("Queue " + (i + 1));
            l.setBounds(col(0), row(i + 3), COL_WIDTH, ROW_HEIGHT);
            add(l);

            var queue = queues.get(i).first;

            if (queue.size() == 0) {
                Logger.log("Closed");
                l = new JLabel("Closed");
                l.setBounds(col(1), row(i + 3), COL_WIDTH, ROW_HEIGHT);
                add(l);
            } else
                for (int j = 0; j < queue.size(); j++) {
                    var task = queue.get(j);

                    Logger.log(task + " ");
                    l = new JLabel(task.toString());
                    l.setBounds(col(j + 1), row(i + 3), COL_WIDTH, ROW_HEIGHT);
                    add(l);
                }
        }

        Logger.log("\n\n\n");

        stopButton.setBounds((WINDOW_WIDTH - COL_WIDTH) / 2, row(queues.size() + 3), COL_WIDTH, ROW_HEIGHT);
        add(stopButton);
    }

    public void setStopButtonListener(ActionListener listener) {
        stopButton.addActionListener(listener);
    }
}
