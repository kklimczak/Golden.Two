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

    private List<List<Double>> errorsPerIteration = new ArrayList<>();

    private int clustersNumb, minXY, maxXY, repeats;

    public KMeans() {
        this.points = new ArrayList<>();
        this.clusters = new ArrayList<>();

        readProperties();
    }

    private void readProperties() {
        this.clustersNumb = Integer.parseInt(properties.getProperty("clusters"));
        this.minXY = Integer.parseInt(properties.getProperty("minXY"));
        this.maxXY = Integer.parseInt(properties.getProperty("maxXY"));
        this.repeats = Integer.parseInt(properties.getProperty("repeats"));
    }

    private void init(String pointsFileName) {
        clusters.clear();
        points.clear();


        points = PointUtil.loadPointsFromFile(pointsFileName);

        if (properties.getProperty("init.method").equals("random partition")) {
            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster();
                Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
                cluster.setCentroid(centroid);
                clusters.add(cluster);
            }
        } else if (properties.getProperty("init.method").equals("forgy")) {
            Collections.shuffle(points);

            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster();
                Point centroid = points.get(i);
                cluster.setCentroid(centroid);
                clusters.add(cluster);
            }
        } else {
            LOG.log(Level.SEVERE, "Cannot recognize init method");
        }
    }

    public void run(String pointsFileName) {
        for (int i = 0; i < repeats; i++) {
            init(pointsFileName);
            calculate();
        }
    }

    private void calculate() {
        boolean finish = false;

        double error, prevError;
        boolean centroidChanged;

        List<Point> prevCentroids = getCentroids();
        List<Point> currentCentroids;
        List<Double> errors = new ArrayList<>();
        prevError = Double.MAX_VALUE;

        while (!finish) {

            clearClusters();

            assignCluster();
            calculateCentroids();

            currentCentroids = getCentroids();
            error = calculateError();

            errors.add((prevError - error) / error);

            centroidChanged = checkIfCentroidMoved(prevCentroids, currentCentroids);
            if (!centroidChanged) {
                finish = true;
            }

            prevError = error;
            prevCentroids = currentCentroids;
        }

        errorsPerIteration.add(new ArrayList<>(errors));
    }

    private boolean checkIfCentroidMoved(List<Point> prevCentroids, List<Point> currentCentroids) {
        for (int i = 0; i < prevCentroids.size(); i++) {
            if (PointUtil.distance(prevCentroids.get(i), currentCentroids.get(i)) != 0) {
                return true;
            }
        }
        return false;
    }

    private double calculateError() {
        double output = 0;
        for (Cluster cluster : clusters) {
            output += cluster.getClusterSquareError();
        }

        return (output / this.clustersNumb);
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
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
