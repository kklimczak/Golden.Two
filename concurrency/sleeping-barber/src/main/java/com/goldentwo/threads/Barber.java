package com.goldentwo.threads;

import com.goldentwo.shop.BarberShop;

import java.util.Random;
import java.util.logging.Logger;

public class Barber extends Thread {

    private static final Logger log = Logger.getLogger(Barber.class.getSimpleName());

    private Random random = new Random();
    private int customersToCut;

    private BarberShop barberShop;

    public Barber(BarberShop barberShop,
                  int customersToCut) {
        super("Barber");
        this.barberShop = barberShop;
        this.customersToCut = customersToCut;
    }

    @Override
    public void run() {
        while (customersToCut != 0) {
            barberShop.askForCustomer();
            cutCustomer();
            barberShop.customerFarewell();
            cleanUpChair();
            customersToCut--;
        }
    }

    private void cleanUpChair() {
        try {
            log.info("Barber is cleaning cut hairs");
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void cutCustomer() {
        try {
            log.info("Barber is cutting " + barberShop.getCustomerFromChair());
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
