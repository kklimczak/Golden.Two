package com.goldentwo.program;

import com.goldentwo.graph.GraphPlot;
import com.goldentwo.utils.AppProperties;
import com.goldentwo.utils.GraphStyle;
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
    private List<List<Cluster>> allEpochClusters;
    private List<Cluster> oneEpochCluster;
    private AppProperties properties = new AppProperties();

    private List<List<Double>> errorsPerIteration = new ArrayList<>();

    private int clustersNumb, minXY, maxXY, repeats;

    private short bestIteration;

    public KMeans() {
        this.points = new ArrayList<>();
        this.allEpochClusters = new ArrayList<>();
        this.oneEpochCluster = new ArrayList<>();

        readProperties();
    }

    private void readProperties() {
        this.clustersNumb = Integer.parseInt(properties.getProperty("clusters"));
        this.minXY = Integer.parseInt(properties.getProperty("minXY"));
        this.maxXY = Integer.parseInt(properties.getProperty("maxXY"));
        this.repeats = Integer.parseInt(properties.getProperty("repeats"));
    }

    private void init(String pointsFileName) {
        oneEpochCluster.clear();
        points.clear();

        points = PointUtil.loadPointsFromFile(pointsFileName);

        if (properties.getProperty("init.method").equals("random partition")) {
            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster();
                Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
                cluster.setCentroid(centroid);
                oneEpochCluster.add(cluster);
            }
        } else if (properties.getProperty("init.method").equals("forgy")) {
            Collections.shuffle(points);

            for (int i = 0; i < clustersNumb; i++) {
                Cluster cluster = new Cluster();
                Point centroid = points.get(i);
                cluster.setCentroid(centroid);
                oneEpochCluster.add(cluster);
            }
        } else {
            LOG.log(Level.SEVERE, "Cannot recognize init method");
        }
    }

    public void run(String pointsFileName) {
        for (int i = 0; i < repeats; i++) {
            init(pointsFileName);
            calculate();
            allEpochClusters.add(new ArrayList<>(oneEpochCluster));
        }

        calculateIterationWithLeastError();
    }

    private void calculateIterationWithLeastError() {
        this.bestIteration = 0;
        double leastError = calculateSummaryError(errorsPerIteration.get(0));
        double localError;

        for (short i = 1; i < errorsPerIteration.size(); i++) {
            localError = calculateSummaryError(errorsPerIteration.get(i));
            if (localError < leastError) {
                leastError = localError;
                this.bestIteration = i;
            }
        }
    }

    private double calculateSummaryError(List<Double> errorList) {
        double output;
        output = errorList.stream().mapToDouble(Double::doubleValue).sum();
        output /= errorList.size();

        return output;
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
            error = calculateClustersError();

            errors.add(Math.abs((prevError - error)));

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

    private double calculateClustersError() {
        double output = 0;
        for (Cluster cluster : oneEpochCluster) {
            output += cluster.getClusterSquareError();
        }

        return (output / this.clustersNumb);
    }

    private void clearClusters() {
        for (Cluster cluster : oneEpochCluster) {
            cluster.clearPoints();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<>(clustersNumb);
        for (Cluster cluster : oneEpochCluster) {
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
                Cluster c = oneEpochCluster.get(i);
                distance = PointUtil.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            oneEpochCluster.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : oneEpochCluster) {
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

        allEpochClusters.get(bestIteration).forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("ATTEMPT NO." + bestIteration, result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    public void plotError() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries errorSeries = new XYSeries("Errors for attemtp no." + bestIteration);

        List<Double> bestIterationErrors = errorsPerIteration.get(bestIteration);

        for (int i = 1; i < bestIterationErrors.size(); i++) {
            errorSeries.add(i, bestIterationErrors.get(i));
        }

        result.addSeries(errorSeries);

        GraphPlot plot = new GraphPlot("Errors", result, GraphStyle.LINE);
        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }
}
