package com.goldentwo.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Monitor {
    private Direction direction;
    private int counter;
    private final Lock lock;
    private final Condition north;
    private final Condition south;

    Monitor(Direction direction) {
        this.direction = direction;
        counter = 0;
        lock = new ReentrantLock(true);
        north = lock.newCondition();
        south = lock.newCondition();
    }

    void dislocate(Car car) throws InterruptedException {
        lock.lock();
        try {
            if (direction != car.getDirection()) {
                if (car.getDirection() == Direction.SOUTH) {
                    while (direction == Direction.NORTH) {
                        south.await();
                    }
                } else {
                    while (direction == Direction.SOUTH) {
                        north.await();
                    }
                }
            }

            counter++;
            System.out.println(
                    "Car " + car.getId() + " has driven through bridge to " +
                    (Direction.SOUTH == car.getDirection() ? "south" : "north"));
            if (counter == 10) {
                if (direction == Direction.SOUTH) {
                    changeDirectionToNorth();
                } else {
                    changeDirectionToSouth();
                }
                counter = 0;
            }

        } finally {
            lock.unlock();
        }

    }

    private void changeDirectionToNorth() {
        direction = Direction.NORTH;
        north.signalAll();
    }

    private void changeDirectionToSouth() {
        direction = Direction.SOUTH;
        south.signalAll();
    }
}
