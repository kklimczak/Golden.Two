package com.goldentwo.shop;

import com.goldentwo.threads.Customer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Salon {

    boolean isBarberReady = false;
    boolean finishedHaircut = false;

    Customer customerInChair;

    Condition invitation;
    Condition readyToShave;
    Condition finished;

    Salon(Lock lock) {
        invitation = lock.newCondition();
        readyToShave = lock.newCondition();
        finished = lock.newCondition();
    }
}
