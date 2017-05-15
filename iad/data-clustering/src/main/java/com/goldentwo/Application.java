package com.goldentwo;

import com.goldentwo.program.KMeans;

public class Application {
    public static void main(String[] args) {
        KMeans kmeans = new KMeans();
        kmeans.run("attract.txt");

        kmeans.plotResultRP();
        kmeans.plotResultForgy();
        kmeans.plotError();
    }
}
