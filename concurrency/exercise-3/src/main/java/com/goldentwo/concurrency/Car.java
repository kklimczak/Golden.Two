package com.goldentwo.concurrency;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car implements Runnable {

    private int id;
    private Direction direction;
    private Monitor monitor;
    private boolean arrived;

    @Override
    public void run() {
        while(!isArrived()) {
            try {
                monitor.dislocate(direction);
                arrived = true;
                System.out.println("Car " + id + " has driven through bridge to " + (Direction.SOUTH == direction ? "south" : "north"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
