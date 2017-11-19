package com.goldentwo.shop;

import com.goldentwo.threads.Customer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class BarberShop {

    private static final Logger log = Logger.getLogger(BarberShop.class.getSimpleName());

    private Lock lock;

    private WainingRoom wainingRoom;
    private Salon salon;

    public BarberShop(int waitingRoomSize) {
        this.lock = new ReentrantLock(true);
        this.wainingRoom = new WainingRoom(lock, waitingRoomSize);
        this.salon = new Salon(lock);
    }

    public void enterBarberShop() {
        lock.lock();

        Thread currentThread = Thread.currentThread();
        try {
            waitingRoomPart(currentThread);
            salonPart(currentThread);
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            currentThread.interrupt();
        } finally {
            lock.unlock();
        }
    }

    private void salonPart(Thread currentThread) throws InterruptedException {
        salon.isBarberReady = false;
        wainingRoom.freeASeat();

        salon.customerInChair = (Customer) currentThread;
        salon.readyToShave.signal();
        log.info(currentThread + " is ready to cut");

        while (!salon.finishedHaircut) {
            salon.finished.await();
        }

        salon.finishedHaircut = false;
    }

    private void waitingRoomPart(Thread currentThread) throws InterruptedException {
        while (isWaitingRoomFull()) {
            log.info(currentThread + " is waiting outside");
            wainingRoom.noSeatsAvailable.await();
        }

        wainingRoom.takeASeat();
        log.info(currentThread + " took a seat in waiting room");

        while (!salon.isBarberReady) {
            log.info(currentThread + " is waiting for barber call");
            salon.invitation.await();
        }
    }

    public void askForCustomer() {
        lock.lock();

        Thread currentThread = Thread.currentThread();
        try {
            while (!isSomeoneInWaitingRoom()) {
                log.info(currentThread + " is waiting for customers");
                wainingRoom.newCustomer.await();
            }

            salon.isBarberReady = true;
            salon.invitation.signal();

            while (salon.customerInChair == null) {
                salon.readyToShave.await();
            }
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void customerFarewell() {
        lock.lock();
        try {
            salon.customerInChair = null;
            salon.finishedHaircut = true;
            salon.finished.signal();
            log.info("Barber cut another client");
        } finally {
            lock.unlock();
        }
    }

    public Customer getCustomerFromChair() {
        return salon.customerInChair;
    }

    private boolean isWaitingRoomFull() {
        return wainingRoom.numberOfWaitingCustomers >= wainingRoom.waitingRoomSize;
    }

    private boolean isSomeoneInWaitingRoom() {
        return wainingRoom.numberOfWaitingCustomers > 0;
    }
}
