package com.goldentwo.utils;

import com.goldentwo.program.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PointUtil {
    public static Point createRandomPoint(int min, int max) {
        Random r = new Random();
        double x = min + (max - min) * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        return new Point(x, y);
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.getY() - p1.getY()), 2) + Math.pow((p2.getX() - p1.getX()), 2));
    }

    public static List<Point> loadPointsFromFile(String fileName) {
        Scanner s = null;
        try {
            s = new Scanner(new File("build/resources/main/" + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Point> output = new ArrayList<>();
        while (s != null && s.hasNext()) {
            String line = s.nextLine();
            String[] split = line.split(",");
            output.add(new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
        }
        if (s != null) {
            s.close();
        }

        return output;
    }
}
