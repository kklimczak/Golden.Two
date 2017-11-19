package com.goldentwo.threads;


import com.goldentwo.shop.BarberShop;

public class Customer extends Thread {

    private BarberShop barberShop;

    public Customer(String name,
                    BarberShop barberShop) {
        super(name);
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        barberShop.enterBarberShop();
    }

    @Override
    public String toString() {
        return getName();
    }
}
