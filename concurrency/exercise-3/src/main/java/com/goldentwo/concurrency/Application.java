package com.goldentwo.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();
        Monitor monitor = new Monitor(Direction.SOUTH);
        ExecutorService executorService = Executors.newFixedThreadPool(40);

        for (int i = 0; i < 40; i++) {
            cars.add(new Car(i, i % 2 == 0 ? Direction.NORTH : Direction.SOUTH, monitor));
        }

        for (Car car : cars) {
            executorService.submit(car);
        }

        executorService.shutdown();
    }

}
