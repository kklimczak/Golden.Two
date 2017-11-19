package com.goldentwo;

import com.goldentwo.shop.BarberShop;
import com.goldentwo.threads.Barber;
import com.goldentwo.threads.Customer;

public class Application {
    private static final int NR_OF_CUSTOMERS = 10;
    private static final int WAITING_ROOM_SIZE = 4;

    public static void main(String[] args) {
        BarberShop barberShop = new BarberShop(WAITING_ROOM_SIZE);

        runCustomers(barberShop);
        runBarber(barberShop);
    }

    private static void runBarber(BarberShop barberShop) {
        new Barber(barberShop, NR_OF_CUSTOMERS).start();
    }

    private static void runCustomers(BarberShop barberShop) {
        for (int i = 0; i < NR_OF_CUSTOMERS; i++) {
            new Customer("Customer" + i, barberShop).start();
        }
    }
}
