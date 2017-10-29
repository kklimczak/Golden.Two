package com.goldentwo.semaphore.util;

import com.goldentwo.semaphore.Application;
import com.goldentwo.semaphore.model.DataModel;
import com.goldentwo.semaphore.thread.CountingThread;
import com.goldentwo.semaphore.thread.GenerateHistogramThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadsRunner {

    private ThreadsRunner() {
    }

    public static void runThreads(DataModel dataModel) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(
                Application.DEFAULT_THREADS_NUMB);

        Semaphore workDoneSemaphore = new Semaphore(Application.DEFAULT_THREADS_NUMB);

        long timeMillisBefore = System.currentTimeMillis();
        runCountingThreads(executor, dataModel, workDoneSemaphore);
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
                                           DataModel dataModel,
                                           Semaphore workDoneSemaphore) throws InterruptedException {
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            workDoneSemaphore.acquire();
            Semaphore binaryLetterSemaphore = new Semaphore(1);

            executor.execute(new CountingThread(
                    dataModel,
                    workDoneSemaphore,
                    binaryLetterSemaphore,
                    String.valueOf(letter),
                    true));

            executor.execute(new CountingThread(
                    dataModel,
                    workDoneSemaphore,
                    binaryLetterSemaphore,
                    String.valueOf(letter),
                    false));
        }
    }
}
