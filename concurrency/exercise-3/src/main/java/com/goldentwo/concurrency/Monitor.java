package com.goldentwo.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Direction direction;
    private int counter;
    private final Lock lock;
    private final Condition north;
    private final Condition south;

    public Monitor(Direction direction) {
        this.direction = direction;
        counter = 0;
        lock = new ReentrantLock(true);
        north = lock.newCondition();
        south = lock.newCondition();
    }

    public void dislocate(Direction carDirection) throws InterruptedException {
        if (direction != carDirection) {
            lock.lock();
            try {
                if (carDirection == Direction.SOUTH) {
                    while (direction == Direction.NORTH) {
                        south.await();
                    }
                } else {
                    while (direction == Direction.SOUTH) {
                        north.await();
                    }
                }
            } finally {
                lock.unlock();
            }
        } else {
            counter++;
            if (counter == 10) {
                if (direction == Direction.SOUTH) {
                    changeDirectionToNorth();
                } else {
                    changeDirectionToSouth();
                }
                counter = 0;
            }
        }
    }

    public void changeDirectionToNorth() {
        lock.lock();
        try {
            direction = Direction.NORTH;
            north.signal();
        } finally {
            lock.unlock();
        }
    }

    public void changeDirectionToSouth() {
        lock.lock();
        try {
            direction = Direction.SOUTH;
            south.signal();
        } finally {
            lock.unlock();
        }
    }
}
