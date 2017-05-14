package com.goldentwo.program;

import com.goldentwo.graph.GraphPlot;
import com.goldentwo.utils.AppProperties;
import com.goldentwo.utils.PointUtil;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.List;

public class KMeans {

    private List<Point> points;
    private List<Cluster> clusters;
    private AppProperties properties = new AppProperties();

    private int clustersNumb, minXY, maxXY;

    public KMeans(String pointsFileName) {
        this.points = new ArrayList<>();
        this.clusters = new ArrayList<>();

        readProperties();
        init(pointsFileName);
    }

    private void readProperties() {
        this.clustersNumb = Integer.parseInt(properties.getProperty("clusters"));
        this.minXY = Integer.parseInt(properties.getProperty("minXY"));
        this.maxXY = Integer.parseInt(properties.getProperty("maxXY"));
    }

    //Initializes the process
    private void init(String pointsFileName) {
        //Create Points
        points = PointUtil.loadPointsFromFile(pointsFileName);


        //Create Clusters
        //Set Random Centroids
        for (int i = 0; i < clustersNumb; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
    }

    private void plotClusters() {
        for (int i = 0; i < clustersNumb; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    //The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while (!finish) {
            //Clear cluster state
            clearClusters();

            List<Point> lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += PointUtil.distance(lastCentroids.get(i), currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            plotClusters();

            if (distance == 0) {
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<>(clustersNumb);
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(), aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min, distance;
        int cluster = 0;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < clustersNumb; i++) {
                Cluster c = clusters.get(i);
                distance = PointUtil.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if (n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }

    public void plotResult() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries pointSeries = new XYSeries("Points");
        XYSeries centroidSeries = new XYSeries("Centroids");
        clusters.forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("PLOT", result);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }
}
