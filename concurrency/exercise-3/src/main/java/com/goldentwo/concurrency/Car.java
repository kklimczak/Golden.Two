package com.goldentwo.concurrency;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car implements Runnable {

    private int id;
    private Direction direction;
    private Monitor monitor;

    @Override
    public void run() {
        try {
            monitor.dislocate(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
