package com.goldentwo.semaphore.util;

import java.util.Random;

public class CharGenerator {
    public static final String CHARS = "ABCDEFGHIJ";

    private static final Random RAND = new Random();

    private CharGenerator() {
    }

    static String generateChar(String chars) {
        return String.valueOf(((CHARS + chars).charAt(RAND.nextInt(CHARS.length() + chars.length()))));
    }
}
