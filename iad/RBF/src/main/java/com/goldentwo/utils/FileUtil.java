package com.goldentwo.utils;

import com.goldentwo.model.other.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    public static List<Point> loadPointsFromFile(String fileName) {
        Scanner s = null;
        try {
            s = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Point> output = new ArrayList<>();
        while (s != null && s.hasNext()) {
            String line = s.nextLine();
            String[] split = line.split(" ");
            output.add(new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
        }
        if (s != null) {
            s.close();
        }

        return output;
    }
}
