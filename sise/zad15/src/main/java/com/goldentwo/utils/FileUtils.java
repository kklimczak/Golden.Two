package com.goldentwo.utils;

import com.goldentwo.models.Board;

import java.io.*;
import java.nio.charset.Charset;

public class FileUtils {

    private static BufferedReader readFile(String fileName) throws IOException {
        InputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        return new BufferedReader(isr);
    }

    public static Board getInitialSetup(String fileName) {
        try {
            BufferedReader br = readFile(fileName);
            Board board = new Board();
            String line;
            String[] lineAfterSplit;
            int counter = -1;
            while ((line = br.readLine()) != null) {
                lineAfterSplit = line.split(" ");
                if (counter == -1) {
                    board.setSizeY(Integer.parseInt(lineAfterSplit[0]));
                    board.setSizeX(Integer.parseInt(lineAfterSplit[1]));
                    board.setNumbers(new int[board.getSizeX()*board.getSizeY()]);
                    counter++;
                } else {
                    for (String aLineSplitted : lineAfterSplit) {
                        board.getNumbers()[counter] = Integer.parseInt(aLineSplitted);
                        if (Integer.parseInt(aLineSplitted) == 0) {
                            board.setBlankTilePosition(counter);
                        }
                        counter++;
                    }
                }
            }

            return board;
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }

    }

}
