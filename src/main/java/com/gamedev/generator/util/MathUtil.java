package com.gamedev.generator.util;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtil {

    public static int getRandIntInRange(int min, int max) {
        if(max == min){
            return min;
        }
        return new Random().nextInt(max - min) + min;
    }

    public static double getRandDoubleInRange(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

}
