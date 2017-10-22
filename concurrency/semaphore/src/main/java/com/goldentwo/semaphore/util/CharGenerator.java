package com.goldentwo.semaphore.util;

import java.util.Random;

public class CharGenerator {
    public static final String CHARS = "ABCDEFGHIJ";

    private static final Random RAND = new Random();

    private CharGenerator() {
    }

    static String getChar() {
        return String.valueOf(CHARS.charAt(RAND.nextInt(CHARS.length())));
    }
}
