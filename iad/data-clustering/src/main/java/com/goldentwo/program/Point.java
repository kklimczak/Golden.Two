package com.goldentwo.program;

public class Point {

    private double x = 0;
    private double y = 0;
    private int cluster_number = 0;

    public Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    Point(Point p){
        this.setX(p.getX());
        this.setY(p.getY());
    }

    void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    void setCluster(int n) {
        this.cluster_number = n;
    }

    public int getCluster() {
        return this.cluster_number;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
