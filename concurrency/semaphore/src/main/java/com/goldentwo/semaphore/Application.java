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
    private static final int WIDTH = 10000;
    private static final int HEIGHT = 1000;

    public static void main(String[] args) throws InterruptedException {
        log.info("Program started");

        ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_THREADS_NUMB + 1);

        String[][] generatedData = DataGenerator.generateData(WIDTH, HEIGHT);
        DataModel dataModel = new DataModel();
        Semaphore workDoneSemaphore = new Semaphore(DEFAULT_THREADS_NUMB);

        runThreads(executor, generatedData, dataModel, workDoneSemaphore);
        runHistogramGeneratorThread(executor, dataModel, workDoneSemaphore);

        executor.shutdown();
    }

    private static void runHistogramGeneratorThread(ExecutorService executor, DataModel dataModel, Semaphore workDoneSemaphore) {
        executor.execute(new GenerateHistogramThread(
                workDoneSemaphore,
                dataModel,
                DEFAULT_THREADS_NUMB
        ));
    }

    private static void runThreads(ExecutorService executor, String[][] generatedData, DataModel dataModel, Semaphore workDoneSemaphore) throws InterruptedException {
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            workDoneSemaphore.acquire();
            executor.execute(new CountingThread(
                    generatedData,
                    dataModel,
                    workDoneSemaphore,
                    String.valueOf(letter)));
        }
    }
}
