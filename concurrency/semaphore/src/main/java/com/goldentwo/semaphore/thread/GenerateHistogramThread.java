package com.goldentwo.semaphore.thread;

import com.goldentwo.semaphore.histogram.Histogram;
import com.goldentwo.semaphore.model.DataModel;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class GenerateHistogramThread implements Runnable {
    private static final Logger log = Logger.getLogger(GenerateHistogramThread.class.getSimpleName());

    private Semaphore workDoneSemaphore;
    private DataModel dataModel;
    private int threadsNumb;

    public GenerateHistogramThread(Semaphore workDoneSemaphore,
                                   DataModel dataModel,
                                   int threadsNumb) {
        this.workDoneSemaphore = workDoneSemaphore;
        this.dataModel = dataModel;
        this.threadsNumb = threadsNumb;
    }

    @Override
    public void run() {
        log.info("Generating histogram thread started");
        boolean tryAcquire = false;
        try {
            while (!tryAcquire) {
                tryAcquire = workDoneSemaphore.tryAcquire(threadsNumb);
                if (!tryAcquire) {
                    Thread.sleep(10);
                }
            }

            new Histogram().generateHistogram(dataModel);
            log.info("Done!");
        } catch (IOException | InterruptedException e) {
            log.warning(e.getMessage());
        } finally {
            workDoneSemaphore.release(threadsNumb);
        }
    }
}
