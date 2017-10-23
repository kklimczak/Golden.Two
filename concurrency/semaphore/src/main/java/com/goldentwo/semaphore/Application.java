package com.goldentwo.semaphore;

import com.goldentwo.semaphore.model.DataModel;
import com.goldentwo.semaphore.thread.CountingThread;
import com.goldentwo.semaphore.thread.GenerateHistogramThread;
import com.goldentwo.semaphore.util.CharGenerator;
import com.goldentwo.semaphore.util.DataGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getSimpleName());
    private static final int DEFAULT_THREADS_NUMB = 10;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    public static void main(String[] args) throws InterruptedException {
        log.info("Program started");

        ExecutorService executor = Executors.newFixedThreadPool(2 * DEFAULT_THREADS_NUMB + 1);
        String[][] generatedData = DataGenerator.generateData(WIDTH, HEIGHT);

        DataModel dataModel = new DataModel();
        Semaphore workDoneSemaphore = new Semaphore(DEFAULT_THREADS_NUMB);

        long timeMillisBefore = System.currentTimeMillis();

        runThreads(executor, generatedData, dataModel, workDoneSemaphore);
        runHistogramGeneratorThread(executor, dataModel, workDoneSemaphore, timeMillisBefore);

        executor.shutdown();
    }

    private static void runHistogramGeneratorThread(ExecutorService executor,
                                                    DataModel dataModel,
                                                    Semaphore workDoneSemaphore,
                                                    long timeMillisBefore) {
        executor.execute(new GenerateHistogramThread(
                workDoneSemaphore,
                dataModel,
                DEFAULT_THREADS_NUMB,
                timeMillisBefore
        ));
    }

    private static void runThreads(ExecutorService executor,
                                   String[][] generatedData,
                                   DataModel dataModel,
                                   Semaphore workDoneSemaphore) throws InterruptedException {
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            workDoneSemaphore.acquire();
            Semaphore letterSemaphore = new Semaphore(1);

            executor.execute(new CountingThread(
                    generatedData,
                    dataModel,
                    workDoneSemaphore,
                    letterSemaphore,
                    String.valueOf(letter),
                    true));

            executor.execute(new CountingThread(
                    generatedData,
                    dataModel,
                    workDoneSemaphore,
                    letterSemaphore,
                    String.valueOf(letter),
                    false));
        }
    }
}
