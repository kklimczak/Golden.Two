package com.goldentwo.strategies;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    public Strategy setParams(String params) {
        System.out.println(params);
        return this;
    }

    public void run() {
        System.out.println("run()");
    }

}
