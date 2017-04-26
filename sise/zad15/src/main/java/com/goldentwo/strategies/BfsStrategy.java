package com.goldentwo.strategies;

public class BfsStrategy implements Strategy {

    public Strategy setParams(String params) {
        System.out.println(params);
        return this;
    }

    public void run() {
        System.out.println("run()");
    }
}
