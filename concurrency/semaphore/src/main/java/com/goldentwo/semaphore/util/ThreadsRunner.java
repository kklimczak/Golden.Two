package com.goldentwo.semaphore.util;

import com.goldentwo.semaphore.Application;
import com.goldentwo.semaphore.model.DataModel;
import com.goldentwo.semaphore.thread.CountingThread;
import com.goldentwo.semaphore.thread.GenerateHistogramThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadsRunner {

    private static final int GENERATE_HISTOGRAM_THREAD = 1;

    private ThreadsRunner() {
    }

    public static void runThreads(String[][] generatedData,
                                  DataModel dataModel) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(
                Application.DEFAULT_THREADS_NUMB + GENERATE_HISTOGRAM_THREAD);

        Semaphore workDoneSemaphore = new Semaphore(Application.DEFAULT_THREADS_NUMB);

        long timeMillisBefore = System.currentTimeMillis();
        runCountingThreads(executor, generatedData, dataModel, workDoneSemaphore);
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
                Application.DEFAULT_THREADS_NUMB,
                timeMillisBefore
        ));
    }

    private static void runCountingThreads(ExecutorService executor,
                                           String[][] generatedData,
                                           DataModel dataModel,
                                           Semaphore workDoneSemaphore) throws InterruptedException {
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            workDoneSemaphore.acquire();
            Semaphore binaryLetterSemaphore = new Semaphore(1);

            executor.execute(new CountingThread(
                    generatedData,
                    dataModel,
                    workDoneSemaphore,
                    binaryLetterSemaphore,
                    String.valueOf(letter),
                    true));

            executor.execute(new CountingThread(
                    generatedData,
                    dataModel,
                    workDoneSemaphore,
                    binaryLetterSemaphore,
                    String.valueOf(letter),
                    false));
        }
    }
}
