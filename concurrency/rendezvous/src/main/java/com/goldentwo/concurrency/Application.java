package com.goldentwo.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static final int TOTAL_CAR_NO = 40;
    private static final int RENDEZVOUS = 1;

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();
        Rendezvous rendezvous = new Rendezvous(Direction.SOUTH, TOTAL_CAR_NO);
        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_CAR_NO + RENDEZVOUS);

        createCars(cars, rendezvous);

        executorService.submit(rendezvous);
        for (Car car : cars) {
            executorService.submit(car);
        }

        executorService.shutdown();
    }

    private static void createCars(List<Car> cars, Rendezvous rendezvous) {
        for (int i = 0; i < TOTAL_CAR_NO; i++) {
            cars.add(
                    new Car(i,
                            i % 2 == 0
                                    ? Direction.NORTH
                                    : Direction.SOUTH,
                            rendezvous)
            );
        }
    }

}
