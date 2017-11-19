package com.goldentwo.shop;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class WainingRoom {

    int numberOfWaitingCustomers = 0;
    int waitingRoomSize;

    Condition noSeatsAvailable;
    Condition newCustomer;

    WainingRoom(Lock lock, int waitingRoomSize) {
        this.noSeatsAvailable = lock.newCondition();
        this.newCustomer = lock.newCondition();
        this.waitingRoomSize = waitingRoomSize;
    }

    void takeASeat() {
        numberOfWaitingCustomers++;
        newCustomer.signal();
    }

    void freeASeat() {
        numberOfWaitingCustomers--;
        noSeatsAvailable.signal();
    }
}
