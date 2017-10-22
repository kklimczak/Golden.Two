package com.goldentwo.semaphore.thread;

import com.goldentwo.semaphore.model.DataModel;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountingThread implements Runnable {

    private static final Logger log = Logger.getLogger(CountingThread.class.getSimpleName());

    private String[][] generatedData;
    private DataModel dataModel;
    private Semaphore workDoneSemaphore;
    private String letter;

    public CountingThread(String[][] generateData,
                          DataModel dataModel,
                          Semaphore workDoneSemaphore,
                          String letter) {
        this.generatedData = generateData;
        this.dataModel = dataModel;
        this.workDoneSemaphore = workDoneSemaphore;
        this.letter = letter;
    }

    @Override
    public void run() {
        log.log(Level.INFO, "Thread {0} started", this.letter);
        for (int row = 0; row < generatedData[0].length; row++) {
            for (int column = 0; column < generatedData[1].length; column++) {
                if (generatedData[row][column].equals(letter)) {
                    Map<String, Integer> integerMap = dataModel.getData();
                    Integer count = integerMap.get(letter);
                    integerMap.put(letter, ++count);
                }
            }
        }

        workDoneSemaphore.release();
    }
}
