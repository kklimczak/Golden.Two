package com.goldentwo.semaphore;

import com.goldentwo.semaphore.model.DataModel;
import com.goldentwo.semaphore.util.DataGenerator;
import com.goldentwo.semaphore.util.ThreadsRunner;

import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getSimpleName());

    private static final String ADDITIONAL_LETTERS = "";
    private static final int WIDTH = 10000;
    private static final int HEIGHT = 1000;

    public static final int DEFAULT_THREADS_NUMB = 20;

    public static void main(String[] args) throws InterruptedException {
        log.info("Program started");

        String[][] generatedData = DataGenerator.generateData(WIDTH, HEIGHT, ADDITIONAL_LETTERS);
        DataModel dataModel = new DataModel();

        ThreadsRunner.runThreads(generatedData, dataModel);
    }
}
