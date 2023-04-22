package com.raru.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    private Util() {
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String formatWords(String s) {
        String[] words = s.split("_");

        for (int i = 0; i < words.length; i++) {
            s = words[i];

            words[i] = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }

        return String.join(" ", words);
    }

}
