package com.goldentwo.semaphore.util;

import java.util.Random;

class CharGenerator {
    private CharGenerator() {
    }

    private static final String CHARS = "abcdefghijklmnopqrstuwvxyz";
    private static Random rand = new Random();

    static char getChar() {
        return CHARS.charAt(rand.nextInt(CHARS.length()));
    }
}
