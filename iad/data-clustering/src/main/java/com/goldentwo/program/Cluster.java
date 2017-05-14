package com.goldentwo.program;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private List<Point> points;
    private Point centroid;
    private int id;

    //Creates a new Cluster
    Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<>();
        this.centroid = null;
    }

    List<Point> getPoints() {
        return points;
    }

    void addPoint(Point point) {
        points.add(point);
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    Point getCentroid() {
        return centroid;
    }

    void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    void clear() {
        points.clear();
    }

    void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.println("[Centroid: " + centroid + "]");
    }

}