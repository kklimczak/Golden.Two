package com.goldentwo.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

class Rendezvous implements Runnable {
    private final BlockingQueue<Car> southQueue;
    private final BlockingQueue<Car> northQueue;

    private Direction direction;
    private int counter;
    private int carsToDislocate;

    Rendezvous(Direction direction,
               int cardToDislocate) {
        this.direction = direction;
        this.carsToDislocate = cardToDislocate;
        southQueue = new SynchronousQueue<>(true);
        northQueue = new SynchronousQueue<>(true);
        counter = 0;
    }

    @Override
    public void run() {
        while (carsToDislocate != 0) {
            if (counter == 10) {
                changeDirection();
                counter = 0;
            }
            dislocate();
        }
    }

    private void dislocate() {
        Car car;
        try {
            if (direction == Direction.NORTH) {
                car = northQueue.take();
                System.out.println("[S --> N] Car" + car.getId());
            } else {
                car = southQueue.take();
                System.out.println("[N --> S] Car" + car.getId());
            }
            counter++;
            carsToDislocate--;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void changeDirection() {
        direction = direction == Direction.NORTH
                ? Direction.SOUTH
                : Direction.NORTH;
    }

    void addToNorthQueue(Car car) throws InterruptedException {
        this.northQueue.put(car);
    }

    void addToSouthQueue(Car car) throws InterruptedException {
        this.southQueue.put(car);
    }
}
