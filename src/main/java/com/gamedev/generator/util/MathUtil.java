package com.gamedev.generator.util;

public class MathUtil {

    public static int getRandIntInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
