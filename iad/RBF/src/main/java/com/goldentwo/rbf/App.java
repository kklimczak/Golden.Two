package com.goldentwo.rbf;

import com.goldentwo.rbf.model.Network;

public class App {
    public static void main(String[] args) {
        Network network = new Network();
        network.run();

        network.plotResult();
        network.plotError();
    }
}
