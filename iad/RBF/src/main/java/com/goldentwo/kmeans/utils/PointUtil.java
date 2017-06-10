package com.goldentwo.kmeans.utils;

import com.goldentwo.rbf.model.other.Point;

public class PointUtil {
    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.getY() - p1.getY()), 2) + Math.pow((p2.getX() - p1.getX()), 2));
    }
}