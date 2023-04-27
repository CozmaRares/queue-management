package com.raru.utils;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static FileWriter writer;

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

    public static void logLine(String message) {
        Logger.log(message + "\n");
    }

    public static void log(String message) {
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
