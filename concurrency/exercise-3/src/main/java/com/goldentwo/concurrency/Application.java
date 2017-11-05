package com.goldentwo.concurrency;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Car> cars = new ArrayList<>();
        Monitor monitor = new Monitor(Direction.SOUTH);

        for (int i = 0; i < 50; i++) {
            cars.add(new Car(i, i % 2 == 0 ? Direction.NORTH : Direction.SOUTH, monitor, false));
        }

        for (Car car : cars) {
            new Thread(car).start();
        }
    }

}
