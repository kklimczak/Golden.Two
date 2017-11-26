package com.goldentwo.concurrency;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;

import static java.lang.Thread.sleep;

@Data
@AllArgsConstructor
public class Car implements Runnable {

    private static final Random RANDOM = new Random();

    private final int id;
    private final Direction direction;
    private final Rendezvous rendezvous;

    @Override
    public void run() {
        try {
            int sleepTime = RANDOM.nextInt(5000);
            sleep(sleepTime);
            if (direction == Direction.NORTH) {
                rendezvous.addToNorthQueue(this);
            } else {
                rendezvous.addToSouthQueue(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
