package com.goldentwo.program;

import com.goldentwo.graph.GraphPlot;
import com.goldentwo.utils.AppProperties;
import com.goldentwo.utils.PointUtil;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KMeans {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private List<Point> points;
    private List<Cluster> clusters;
    private AppProperties properties = new AppProperties();

    private int clustersNumb, minXY, maxXY;
    private double epsilon;

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
        this.epsilon = Double.parseDouble(properties.getProperty("epsilon"));
    }

    private void init(String pointsFileName) {
        points = PointUtil.loadPointsFromFile(pointsFileName);

        if (properties.getProperty("init.method").equals("random partition")) {
            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster(i);
                Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
                cluster.setCentroid(centroid);
                clusters.add(cluster);
            }
        } else if (properties.getProperty("init.method").equals("forgy")) {
            Collections.shuffle(points);

            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster(i);
                Point centroid = points.get(i);
                cluster.setCentroid(centroid);
                clusters.add(cluster);
            }
        } else {
            LOG.log(Level.SEVERE, "Cannot recognize init method");
        }
    }

    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        while (!finish) {

            clearClusters();

            List<Point> lastCentroids = getCentroids();

            assignCluster();
            calculateCentroids();

            List<Point> currentCentroids = getCentroids();

            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += PointUtil.distance(lastCentroids.get(i), currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + (++iteration));
            System.out.println("Centroid distances: " + distance);

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
