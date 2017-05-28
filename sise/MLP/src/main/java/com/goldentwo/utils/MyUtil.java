package com.goldentwo.utils;

public class MyUtil {
    public static double randomNumber(double min, double max) {
        return min + Math.random() * (max - min);
    }
}
