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
import java.util.logging.Logger;

public class KMeans {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private List<Point> points;
    private List<List<Cluster>> allEpochClustersRP;
    private List<List<Cluster>> allEpochClustersForgy;
    private List<Cluster> oneEpochClusterRP;
    private List<Cluster> oneEpochClusterForgy;

    private AppProperties properties = new AppProperties();

    private List<List<Double>> errorsPerIterationRP = new ArrayList<>();
    private List<List<Double>> errorsPerIterationForgy = new ArrayList<>();

    private int clustersNumb, minXY, maxXY, repeats;

    private short bestIterationRP, bestIterationForgy;

    public KMeans() {
        this.points = new ArrayList<>();
        this.allEpochClustersRP = new ArrayList<>();
        this.allEpochClustersForgy = new ArrayList<>();
        this.oneEpochClusterRP = new ArrayList<>();
        this.oneEpochClusterForgy = new ArrayList<>();

        readProperties();
    }

    private void readProperties() {
        this.clustersNumb = Integer.parseInt(properties.getProperty("clusters"));
        this.minXY = Integer.parseInt(properties.getProperty("minXY"));
        this.maxXY = Integer.parseInt(properties.getProperty("maxXY"));
        this.repeats = Integer.parseInt(properties.getProperty("repeats"));
    }

    private void init(String pointsFileName) {
        oneEpochClusterRP.clear();
        oneEpochClusterForgy.clear();
        points.clear();

        points = PointUtil.loadPointsFromFile(pointsFileName);

        for (int i = 0; i < clustersNumb; i++) {
            Cluster cluster = new Cluster();
            Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
            cluster.setCentroid(centroid);
            oneEpochClusterRP.add(cluster);
        }

        Collections.shuffle(points);
        for (int i = 0; i < clustersNumb; i++) {
            Cluster cluster = new Cluster();
            Point centroid = points.get(i);
            cluster.setCentroid(centroid);
            oneEpochClusterForgy.add(cluster);
        }

    }

    public void run(String pointsFileName) {
        for (int i = 0; i < repeats; i++) {
            init(pointsFileName);
            calculate(false);
            calculate(true);
            allEpochClustersRP.add(new ArrayList<>(oneEpochClusterRP));
            allEpochClustersForgy.add(new ArrayList<>(oneEpochClusterForgy));
        }

        calculateIterationWithLeastError();
    }

    private void calculateIterationWithLeastError() {
        this.bestIterationRP = 0;
        this.bestIterationForgy = 0;

        double leastErrorRP = calculateSummaryError(errorsPerIterationRP.get(0));
        double leastErrorForgy = calculateSummaryError(errorsPerIterationForgy.get(0));
        double localError;

        for (short i = 1; i < errorsPerIterationRP.size(); i++) {
            localError = calculateSummaryError(errorsPerIterationRP.get(i));
            if (localError < leastErrorRP) {
                leastErrorRP = localError;
                this.bestIterationRP = i;
            }
        }
        for (short i = 1; i < errorsPerIterationForgy.size(); i++) {
            localError = calculateSummaryError(errorsPerIterationForgy.get(i));
            if (localError < leastErrorForgy) {
                leastErrorForgy = localError;
                this.bestIterationForgy = i;
            }
        }
    }

    private double calculateSummaryError(List<Double> errorList) {
        double output = errorList.stream().mapToDouble(Double::doubleValue).sum();
        output /= errorList.size();

        return output;
    }

    private void calculate(boolean isRandomPartition) {
        boolean finish = false;

        double error;
        boolean centroidChanged;

        List<Point> prevCentroids = getCentroids(isRandomPartition);
        List<Point> currentCentroids;
        List<Double> errors = new ArrayList<>();

        while (!finish) {

            clearClusters(isRandomPartition);

            assignCluster(isRandomPartition);
            calculateCentroids(isRandomPartition);

            currentCentroids = getCentroids(isRandomPartition);
            error = calculateClustersError(isRandomPartition);

            errors.add(Math.abs(error));

            centroidChanged = checkIfCentroidMoved(prevCentroids, currentCentroids);
            if (!centroidChanged) {
                finish = true;
            }

            prevCentroids = currentCentroids;
        }
        if (isRandomPartition) {
            errorsPerIterationRP.add(new ArrayList<>(errors));
        } else {
            errorsPerIterationForgy.add(new ArrayList<>(errors));
        }
    }

    private boolean checkIfCentroidMoved(List<Point> prevCentroids, List<Point> currentCentroids) {
        for (int i = 0; i < prevCentroids.size(); i++) {
            if (PointUtil.distance(prevCentroids.get(i), currentCentroids.get(i)) != 0) {
                return true;
            }
        }
        return false;
    }

    private double calculateClustersError(boolean isRandomPartition) {
        double output = 0;
        List<Cluster> clusterTocalculateError = isRandomPartition ? oneEpochClusterRP : oneEpochClusterForgy;
        for (Cluster cluster : clusterTocalculateError) {
            output += cluster.getClusterSquareError();
        }

        return (output / this.clustersNumb);
    }

    private void clearClusters(boolean isRandomPartition) {
        List<Cluster> clusterToClear = isRandomPartition ? oneEpochClusterRP : oneEpochClusterForgy;
        for (Cluster cluster : clusterToClear) {
            cluster.clearPoints();
        }
    }

    private List<Point> getCentroids(boolean isRandomPartition) {
        List<Point> centroids = new ArrayList<>(clustersNumb);
        List<Cluster> clusterToGetCentroids = isRandomPartition ? oneEpochClusterRP : oneEpochClusterForgy;
        for (Cluster cluster : clusterToGetCentroids) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(), aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster(boolean isRandomPartition) {
        double max = Double.MAX_VALUE;
        double min, distance;
        int cluster = 0;

        List<Cluster> clusterToAssign = isRandomPartition ? oneEpochClusterRP : oneEpochClusterForgy;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < clustersNumb; i++) {
                Cluster c = clusterToAssign.get(i);
                distance = PointUtil.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusterToAssign.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids(boolean isRandomPartition) {
        List<Cluster> clusterToCalculate = isRandomPartition ? oneEpochClusterRP : oneEpochClusterForgy;

        for (Cluster cluster : clusterToCalculate) {
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

    public void plotResultRP() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries pointSeries = new XYSeries("Points");
        XYSeries centroidSeries = new XYSeries("Centroids");

        allEpochClustersRP.get(bestIterationRP).forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("Random partition ATTEMPT NO." + bestIterationRP, result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    public void plotError() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries errorSeriesRP = new XYSeries("Random partition");
        XYSeries errorSeriesForgy = new XYSeries("Forgy");

        List<Double> errorsRP = calculateAllEpochErrors(true);
        List<Double> errorsForgy = calculateAllEpochErrors(false);

        for (int i = 0; i < errorsRP.size(); i++) {
            errorSeriesRP.add(i, errorsRP.get(i));
        }
        for (int i = 0; i < errorsForgy.size(); i++) {
            errorSeriesForgy.add(i, errorsForgy.get(i));
        }

        result.addSeries(errorSeriesRP);
        result.addSeries(errorSeriesForgy);

        GraphPlot plot = new GraphPlot("Errors", result, GraphStyle.LINE);
        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    private List<Double> calculateAllEpochErrors(boolean isRandomPartition) {
        List<List<Double>> errorsToCalculate = isRandomPartition ? errorsPerIterationRP : errorsPerIterationForgy;
        List<Double> output = new ArrayList<>();
        double tmp;

        int minSize = Integer.MAX_VALUE;

        for (List<Double> anErrorsToCalculate : errorsToCalculate) {
            if (anErrorsToCalculate.size() < minSize) {
                minSize = anErrorsToCalculate.size();
            }
        }

        for (int i = 0; i < minSize; i++) {
            tmp = 0.0;
            for (List<Double> anErrorsToCalculate : errorsToCalculate) {
                tmp += anErrorsToCalculate.get(i);
            }
            tmp /= repeats;
            output.add(tmp);
        }

        return output;
    }

    public void plotResultForgy() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries pointSeries = new XYSeries("Points");
        XYSeries centroidSeries = new XYSeries("Centroids");

        allEpochClustersForgy.get(bestIterationForgy).forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("Forgy ATTEMPT NO." + bestIterationForgy, result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }
}
