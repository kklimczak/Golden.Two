package com.goldentwo.kmeans;

import com.goldentwo.kmeans.utils.PointUtil;
import com.goldentwo.rbf.model.other.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class KMeans {

    private List<Point> points;
    private List<List<Cluster>> allRepetitionClustersForgy = new ArrayList<>();
    private List<Cluster> currentRepetitionClusterForgy = new ArrayList<>();

    private List<List<Double>> errorsPerRepetitionForgy = new ArrayList<>();

    private int clustersNumb, repeats;

    private short bestRepetitionForgy;

    public KMeans(List<Point> points, int clustersNumb, int repeats) {
        this.points = points;
        this.clustersNumb = clustersNumb;
        this.repeats = repeats;
    }

    public void run() {
        System.out.println("Clusters calculating started...");
        long timeBefore = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            cleanUp();
            proceed();
            allRepetitionClustersForgy.add(new ArrayList<>(currentRepetitionClusterForgy));
        }
        findRepetitionWithLeastError();

        long timeAfter = System.currentTimeMillis();
        System.out.println("Clusters calculated after " + (timeAfter - timeBefore) + "ms");
    }

    public List<Point> getCalculaterdClustersCenters() {
        return allRepetitionClustersForgy.get(bestRepetitionForgy)
                .stream()
                .map(cluster -> new Point(cluster.getCentroid().getX(), cluster.getCentroid().getY()))
                .collect(Collectors.toList());
    }

    private void cleanUp() {
        currentRepetitionClusterForgy.clear();
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


    private void findRepetitionWithLeastError() {
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

    private double calculateSummaryError(List<Double> errorList) {
        double output = errorList.stream().mapToDouble(Double::doubleValue).sum();
        output /= errorList.size();

        return output;
    }

    private void proceed() {
        boolean finish = false;

        double error;
        boolean centroidChanged;

        List<Point> prevCentroids = getCentroids();
        List<Point> currentCentroids;
        List<Double> errors = new ArrayList<>();

        while (!finish) {

            clearClusters();

            assignPointsToClosestCluster();
            calculateCentroids();

            currentCentroids = getCentroids();
            error = calculateClustersError();

            errors.add(Math.abs(error));

            centroidChanged = checkIfCentroidMoved(prevCentroids, currentCentroids);
            if (!centroidChanged) {
                finish = true;
            }

            prevCentroids = currentCentroids;
        }

        errorsPerRepetitionForgy.add(new ArrayList<>(errors));

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
        for (Cluster cluster : currentRepetitionClusterForgy) {
            output += cluster.getClusterSquareError();
        }

        output /= this.clustersNumb;

        return output;
    }

    private void clearClusters() {
        for (Cluster cluster : currentRepetitionClusterForgy) {
            cluster.clearPoints();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<>(clustersNumb);
        for (Cluster cluster : currentRepetitionClusterForgy) {
            Point centroidCopy = new Point(cluster.getCentroid());
            centroids.add(centroidCopy);
        }
        return centroids;
    }

    private void assignPointsToClosestCluster() {
        double max = Double.MAX_VALUE;
        double min, distance;
        int closestCluster = 0;

        for (Point point : points) {
            min = max;
            for (int i = 0; i < clustersNumb; i++) {
                Cluster c = currentRepetitionClusterForgy.get(i);
                distance = PointUtil.distance(point, c.getCentroid());
                if (distance < min) {
                    min = distance;
                    closestCluster = i;
                }
            }

            currentRepetitionClusterForgy.get(closestCluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : currentRepetitionClusterForgy) {
            double pointsSummaryX = 0;
            double pointsSummaryY = 0;
            List<Point> list = cluster.getPoints();
            int pointsAmount = list.size();

            for (Point point : list) {
                pointsSummaryX += point.getX();
                pointsSummaryY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if (pointsAmount > 0) {
                double centroidX = pointsSummaryX / pointsAmount;
                double centroidY = pointsSummaryY / pointsAmount;

                centroid.setLocation(centroidX, centroidY);
            }
        }
    }

    public int getClustersNumb() {
        return clustersNumb;
    }
}
