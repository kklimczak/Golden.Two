package com.goldentwo.utils;

import com.goldentwo.models.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static Node readNodeFromFile(String fileName) {
        Path path = Paths.get(fileName);
        List<Integer> fileValues = readFileValues(path);

        Node output = new Node();

        int sizeY = fileValues.get(0);
        int sizeX = fileValues.get(1);

        output.setSizeX(sizeX);
        output.setSizeY(sizeY);

        int[] numbers = new int[sizeX * sizeY];

        for (int i = 2; i < fileValues.size(); i++) {
            numbers[i - 2] = fileValues.get(i);
        }

        int blankTaleIndex = getBlankTaleIndex(numbers);
        output.setBlankTilePosition(blankTaleIndex);

        output.setNumbers(numbers);

        return output;
    }

    private static int getBlankTaleIndex(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                return i;
            }
        }
        return 0;
    }

    private static List<Integer> readFileValues(Path path) {
        List<Integer> output = new ArrayList<>();
        try {
            Files.lines(path).forEach(
                    line -> {
                        String[] split = line.split(" ");
                        for (String lineElement : split) {
                            output.add(Integer.valueOf(lineElement));
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

}
