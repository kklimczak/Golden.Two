package com.goldentwo.strategies;

public interface Strategy {

    Strategy setParams(String runParam);
    void run();

}
