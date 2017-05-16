package com.goldentwo;

import com.goldentwo.program.KMeans;

public class Application {

    private static KMeans kmeans;

    public static void main(String[] args) {
        kmeans = new KMeans();
        kmeans.run("attract.txt");
        plot();
    }

    private static void plot() {
        kmeans.plotResultRP();
        kmeans.plotResultForgy();
        kmeans.plotError();
    }
}
