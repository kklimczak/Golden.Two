package com.goldentwo;

import com.goldentwo.program.KMeans;

public class Application {
    public static void main(String[] args) {
        KMeans kmeans = new KMeans("attract.txt");
        kmeans.calculate();
        kmeans.plotResult();
    }
}
