package com.goldentwo.semaphore.thread;

import com.goldentwo.semaphore.model.DataModel;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountingThread implements Runnable {

    private static final Logger log = Logger.getLogger(CountingThread.class.getSimpleName());

    private DataModel dataModel;
    private Semaphore workDoneSemaphore;
    private Semaphore letterSemaphore;
    private String letter;
    private boolean countFromBeginning;

    public CountingThread(DataModel dataModel,
                          Semaphore workDoneSemaphore,
                          Semaphore letterSemaphore,
                          String letter,
                          boolean countFromBeginning) {
        this.dataModel = dataModel;
        this.workDoneSemaphore = workDoneSemaphore;
        this.letterSemaphore = letterSemaphore;
        this.letter = letter;
        this.countFromBeginning = countFromBeginning;
    }

    @Override
    public void run() {
        log.log(Level.INFO, "Thread {0} started", Thread.currentThread().getName());
        String[][] generatedData = this.dataModel.getData();

        if (this.countFromBeginning) {
            for (int row = 0; row < generatedData.length / 2; row++) {
                for (int column = 0; column < generatedData[row].length; column++) {
                    incrementLetterCounter(generatedData[row][column]);
                }
            }
        } else {
            for (int row = generatedData.length / 2; row < generatedData.length; row++) {
                for (int column = 0; column < generatedData[row].length; column++) {
                    incrementLetterCounter(generatedData[row][column]);
                }
            }
        }

        workDoneSemaphore.release();
    }

    private void incrementLetterCounter(String letter) {
        if (letter.equals(this.letter)) {
            try {
                letterSemaphore.acquire();
                dataModel.incrementLetterCounter(this.letter);
            } catch (InterruptedException e) {
                log.log(Level.OFF, e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                letterSemaphore.release();
            }
        }
    }
}
