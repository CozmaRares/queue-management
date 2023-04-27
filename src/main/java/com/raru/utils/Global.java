package com.raru.utils;

public class Global {
    private static int timeUnitDuration = 1000;
    private static int maxQueueSize = 5;

    public static void setTimeUnitDuration(int durationMs) {
        Global.timeUnitDuration = durationMs;
    }

    public static int getTimeUnitDuration() {
        return Global.timeUnitDuration;
    }

    public static void setMaxQueueSize(int maxQueueSize) {
        Global.maxQueueSize = maxQueueSize;
    }

    public static int getMaxQueueSize() {
        return Global.maxQueueSize != -1
                ? Global.maxQueueSize
                : Integer.MAX_VALUE;
    }

    private Global() {
    }
}
