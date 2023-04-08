package com.raru.view;

import javax.swing.*;

import com.raru.model.data.SimulationFrame;

public class SimulationView extends JFrame {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;

    private static final int ROW_GAP = 10;
    private static final int COL_GAP = 10;

    private static final int COL_WIDTH = 100;
    private static final int ROW_HEIGHT = 30;

    // private static final int WINDOW_WIDTH = MARGIN_X * 2 + COL_GAP + COL0_WIDTH +
    // COL1_WIDTH;
    // private static final int WINDOW_HEIGHT = row(9) + ROW_HEIGHT + MARGIN_Y;

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = 800;

    private static int row(int rowNumber) {
        return MARGIN_Y + rowNumber * (ROW_HEIGHT + ROW_GAP);
    }

    private static int col(int colNumber) {
        return MARGIN_X + colNumber * (COL_WIDTH + COL_GAP);
    }

    public SimulationView(SimulationFrame frame) {
        setLayout(null);
        setVisible(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFrame(frame);
    }

    public void setFrame(SimulationFrame frame) {
        getContentPane().removeAll();

        var queues = frame.getQueues();

        for (int i = 0; i < queues.size(); i++) {
            var queue = queues.get(i);

            var l = new JLabel("Server " + (i + 1));
            l.setBounds(col(0), row(i), COL_WIDTH, ROW_HEIGHT);
            add(l);

            for (int j = 0; j < queue.size(); j++) {
                l = new JLabel(queue.get(j).toString());
                l.setBounds(col(j + 1), row(i), COL_WIDTH, ROW_HEIGHT);
                add(l);
            }
        }

        validate();
        repaint();
    }
}
