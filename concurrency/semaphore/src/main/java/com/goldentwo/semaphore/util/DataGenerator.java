package com.goldentwo.semaphore.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final Logger log = Logger.getLogger(DataGenerator.class.getSimpleName());

    public static String[][] generateData(int width, int height) {
        log.log(Level.INFO, "Generating 2D data with {0} width and " + height + " height", width);
        String[][] data = new String[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = CharGenerator.getChar();
            }
        }

        saveToFile(data);

        return data;
    }

    private static void saveToFile(String[][] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data[1].length; j++) {
                builder.append(data[i][j]);
                if (j < data[1].length - 1)
                    builder.append(",");
            }
            builder.append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            writer.write(builder.toString());
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }
}
