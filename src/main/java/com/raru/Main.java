package com.raru;

import java.util.ArrayList;

import com.raru.model.data.SimulationFrame;
import com.raru.model.data.Task.ImmutableTask;
import com.raru.view.*;

public class Main {
    public static void main(String[] args) {
        var f = new SimulationFrame(new ArrayList<>());

        ArrayList<ImmutableTask> l;

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        var mata = new SimulationView(f);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        f = new SimulationFrame(new ArrayList<>());

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        l = new ArrayList<>();
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        l.add(new ImmutableTask(0, 0));
        f.addQueue(l);

        System.out.println("mata");
        mata.setFrame(f);
    }
}
