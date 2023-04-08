package com.raru.utils;

import  java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static int integer(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
