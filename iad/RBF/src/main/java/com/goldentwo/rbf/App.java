package com.goldentwo.rbf;

import com.goldentwo.rbf.model.Network;

public class App {
    public static void main(String[] args) {
        Network network = new Network(5);
        network.run(1000, 0.01);

        network.plotApproximation();

    }
}
