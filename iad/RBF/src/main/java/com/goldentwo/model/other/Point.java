package com.goldentwo.model.other;

import lombok.AllArgsConstructor;

import java.awt.geom.Point2D;

@AllArgsConstructor
public class Point extends Point2D {

    private double x;
    private double y;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
