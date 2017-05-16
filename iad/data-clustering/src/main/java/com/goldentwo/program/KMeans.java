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

    private AppProperties properties = new AppProperties();

    private List<Point> points;
    private List<List<Cluster>> allRepetitionClustersRP;
    private List<List<Cluster>> allRepetitionClustersForgy;
    private List<Cluster> currentRepetitionClusterRP;
    private List<Cluster> currentRepetitionClusterForgy;

    private List<List<Double>> errorsPerRepetitionRP;
    private List<List<Double>> errorsPerRepetitionForgy;

    private int clustersNumb, minXY, maxXY, repeats;

    private short bestRepetitionRP, bestRepetitionForgy;

    public KMeans() {
        this.points = new ArrayList<>();
        this.allRepetitionClustersRP = new ArrayList<>();
        this.allRepetitionClustersForgy = new ArrayList<>();
        this.currentRepetitionClusterRP = new ArrayList<>();
        this.currentRepetitionClusterForgy = new ArrayList<>();
        this.errorsPerRepetitionRP = new ArrayList<>();
        this.errorsPerRepetitionForgy = new ArrayList<>();

        readProperties();
    }

    private void readProperties() {
        this.clustersNumb = Integer.parseInt(properties.getProperty("clusters"));
        this.minXY = Integer.parseInt(properties.getProperty("minXY"));
        this.maxXY = Integer.parseInt(properties.getProperty("maxXY"));
        this.repeats = Integer.parseInt(properties.getProperty("repeats"));
    }

    private void init(String pointsFileName) {
        currentRepetitionClusterRP.clear();
        currentRepetitionClusterForgy.clear();
        points.clear();

        points = PointUtil.loadPointsFromFile(pointsFileName);

        initClustersRP();
        initClustersForgy();

    }

    private void initClustersForgy() {
        Collections.shuffle(points);
        for (int i = 0; i < clustersNumb; i++) {
            Cluster cluster = new Cluster();
            Point centroid = points.get(i);
            cluster.setCentroid(centroid);
            currentRepetitionClusterForgy.add(cluster);
        }
    }

    private void initClustersRP() {
        for (int i = 0; i < clustersNumb; i++) {
            Cluster cluster = new Cluster();
            Point centroid = PointUtil.createRandomPoint(minXY, maxXY);
            cluster.setCentroid(centroid);
            currentRepetitionClusterRP.add(cluster);
        }
    }

    public void run(String pointsFileName) {
        for (int i = 0; i < repeats; i++) {
            init(pointsFileName);

            proceed(false);
            proceed(true);

            allRepetitionClustersRP.add(new ArrayList<>(currentRepetitionClusterRP));
            allRepetitionClustersForgy.add(new ArrayList<>(currentRepetitionClusterForgy));
        }

        findRepetitionWithLeastError();
    }

    private void findRepetitionWithLeastError() {
        findRPRepetition();
        findForgyRepetition();
    }

    private void findForgyRepetition() {
        this.bestRepetitionForgy = 0;
        double leastErrorForgy = calculateSummaryError(errorsPerRepetitionForgy.get(0));
        double localError;
        for (short i = 1; i < errorsPerRepetitionForgy.size(); i++) {
            localError = calculateSummaryError(errorsPerRepetitionForgy.get(i));
            if (localError < leastErrorForgy) {
                leastErrorForgy = localError;
                this.bestRepetitionForgy = i;
            }
        }
    }

    private void findRPRepetition() {
        this.bestRepetitionRP = 0;
        double leastErrorRP = calculateSummaryError(errorsPerRepetitionRP.get(0));
        double localError;
        for (short i = 1; i < errorsPerRepetitionRP.size(); i++) {
            localError = calculateSummaryError(errorsPerRepetitionRP.get(i));
            if (localError < leastErrorRP) {
                leastErrorRP = localError;
                this.bestRepetitionRP = i;
            }
        }
    }

    private double calculateSummaryError(List<Double> errorList) {
        double output = errorList.stream().mapToDouble(Double::doubleValue).sum();
        output /= errorList.size();

        return output;
    }

    private void proceed(boolean isRandomPartition) {
        boolean finish = false;

        double error;
        boolean centroidChanged;

        List<Point> prevCentroids = getCentroids(isRandomPartition);
        List<Point> currentCentroids;
        List<Double> errors = new ArrayList<>();

        while (!finish) {

            clearClusters(isRandomPartition);

            assignPointsToCluster(isRandomPartition);
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
            errorsPerRepetitionRP.add(new ArrayList<>(errors));
        } else {
            errorsPerRepetitionForgy.add(new ArrayList<>(errors));
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
        List<Cluster> clusterToCalculateError = isRandomPartition ? currentRepetitionClusterRP : currentRepetitionClusterForgy;
        for (Cluster cluster : clusterToCalculateError) {
            output += cluster.getClusterSquareError();
        }

        output /= this.clustersNumb;

        return output;
    }

    private void clearClusters(boolean isRandomPartition) {
        List<Cluster> clusterToClear = isRandomPartition ? currentRepetitionClusterRP : currentRepetitionClusterForgy;
        for (Cluster cluster : clusterToClear) {
            cluster.clearPoints();
        }
    }

    private List<Point> getCentroids(boolean isRandomPartition) {
        List<Point> centroids = new ArrayList<>(clustersNumb);
        List<Cluster> clusterToGetCentroids = isRandomPartition ? currentRepetitionClusterRP : currentRepetitionClusterForgy;
        for (Cluster cluster : clusterToGetCentroids) {
            Point centroidCopy = new Point(cluster.getCentroid());
            centroids.add(centroidCopy);
        }
        return centroids;
    }

    private void assignPointsToCluster(boolean isRandomPartition) {
        double max = Double.MAX_VALUE;
        double min, distance;
        int cluster = 0;

        List<Cluster> clusterToAssign = isRandomPartition ? currentRepetitionClusterRP : currentRepetitionClusterForgy;

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
        List<Cluster> clusterToCalculate = isRandomPartition ? currentRepetitionClusterRP : currentRepetitionClusterForgy;

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

        allRepetitionClustersRP.get(bestRepetitionRP).forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("Random partition ATTEMPT NO." + bestRepetitionRP, result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    public void plotError() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries errorSeriesRP = new XYSeries("Random partition");
        XYSeries errorSeriesForgy = new XYSeries("Forgy");

        XYSeries errorDiff = new XYSeries("Error diff");

        List<Double> errorsRP = calculateAllEpochErrors(true);
        List<Double> errorsForgy = calculateAllEpochErrors(false);

        int sizeRP = errorsRP.size();
        int sizeForgy = errorsForgy.size();

        for (int i = 0; i < sizeRP; i++) {
            errorSeriesRP.add(i, errorsRP.get(i));
        }
        for (int i = 0; i < sizeForgy; i++) {
            errorSeriesForgy.add(i, errorsForgy.get(i));
        }

        for (int i = 0; i < sizeRP && i < sizeForgy; i++) {
            errorDiff.add(i, Math.abs(errorsRP.get(i) - errorsForgy.get(i)));
        }

        result.addSeries(errorSeriesRP);
        result.addSeries(errorSeriesForgy);
        result.addSeries(errorDiff);

        GraphPlot plot = new GraphPlot("Errors", result, GraphStyle.LINE);
        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    private List<Double> calculateAllEpochErrors(boolean isRandomPartition) {
        List<List<Double>> errorsToCalculate = isRandomPartition ? errorsPerRepetitionRP : errorsPerRepetitionForgy;
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

        allRepetitionClustersForgy.get(bestRepetitionForgy).forEach(
                cluster -> centroidSeries.add(cluster.getCentroid().getX(), cluster.getCentroid().getY())
        );

        points.forEach(
                point -> pointSeries.add(point.getX(), point.getY())
        );

        result.addSeries(centroidSeries);
        result.addSeries(pointSeries);
        GraphPlot plot = new GraphPlot("Forgy ATTEMPT NO." + bestRepetitionForgy, result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }
}
