package com.raru.utils;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static FileWriter writer;
    private static LogLevel acceptedLevel = LogLevel.ALL;

    private Logger() {
    }

    public static void openFile(String path) {
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLevel(LogLevel level) {
        Logger.acceptedLevel = level;
    }

    public static void logLine(String message, LogLevel level) {
        Logger.log(message + "\n", level);
    }

    public static void log(String message, LogLevel level) {
        if (level.equals(Logger.acceptedLevel) || Logger.acceptedLevel.equals(LogLevel.ALL))
            try {
                writer.write(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public enum LogLevel {
        ALL, TASK_PARTITION, THREAD_LIFETIME, SIMULATION_FRAME
    }
}