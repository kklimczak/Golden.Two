package com.goldentwo.program;

import com.goldentwo.utils.PointUtil;

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

    public double getClusterSquareError() {
        double temp = 0;
        double distance;
        for (Point point : points) {
            distance = PointUtil.distance(point, this.centroid);
            temp += distance * distance;
        }
        return temp / points.size();
    }

}