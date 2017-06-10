package com.goldentwo.kmeans;

import com.goldentwo.kmeans.utils.PointUtil;
import com.goldentwo.rbf.model.other.Point;

import java.util.ArrayList;
import java.util.List;

class Cluster {

    private List<Point> points;
    private Point centroid;

    Cluster() {
        this.points = new ArrayList<>();
        this.centroid = null;
    }

    List<Point> getPoints() {
        return points;
    }

    void addPoint(Point point) {
        points.add(point);
    }

    Point getCentroid() {
        return centroid;
    }

    void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    void clearPoints() {
        points.clear();
    }

    double getClusterSquareError() {
        double temp = 0;
        double distance;
        if (points.size() <= 0) {
            return 0;
        }
        for (Point point : points) {
            distance = PointUtil.distance(point, this.centroid);
            temp += distance * distance;
        }
        return temp / points.size();
    }

}
