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
    private Semaphore letterSemaphore;
    private String letter;
    private boolean countFromBeginning;

    public CountingThread(String[][] generateData,
                          DataModel dataModel,
                          Semaphore workDoneSemaphore,
                          Semaphore letterSemaphore,
                          String letter,
                          boolean countFromBeginning) {
        this.generatedData = generateData;
        this.dataModel = dataModel;
        this.workDoneSemaphore = workDoneSemaphore;
        this.letterSemaphore = letterSemaphore;
        this.letter = letter;
        this.countFromBeginning = countFromBeginning;
    }

    @Override
    public void run() {
        log.log(Level.INFO, "Thread {0} started. Counting from beginning: " + this.countFromBeginning, this.letter);

        if (this.countFromBeginning) {
            for (int row = 0; row <= generatedData[0].length / 2; row++) {
                for (int column = 0; column < generatedData[1].length / 2; column++) {
                    countLetter(generatedData[row][column]);
                }
            }
        } else {
            for (int row = generatedData[0].length / 2; row >= 0; row--) {
                for (int column = generatedData[1].length / 2; column >= 0; column--) {
                    countLetter(generatedData[row][column]);
                }
            }
        }

        workDoneSemaphore.release();
    }

    private void countLetter(String letter) {
        boolean tryAcquire = false;
        if (letter.equals(this.letter)) {
            while (!tryAcquire) {
                tryAcquire = letterSemaphore.tryAcquire();
            }

            Map<String, Integer> integerMap = dataModel.getData();
            Integer count = integerMap.get(this.letter);
            integerMap.put(this.letter, ++count);

            letterSemaphore.release();
        }
    }
}
