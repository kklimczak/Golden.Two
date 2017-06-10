package com.goldentwo.rbf.model.neurons;

import com.goldentwo.rbf.model.other.Point;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RadianNeuron {
    private double unitCenter;
    private double unitWidth;

    public RadianNeuron(double unitCenter) {
        this.unitCenter = unitCenter;
    }

    public double calculateOutput(double input) {
        return gaussianActivation(input);
    }

    public void calculateUnitWidth(List<RadianNeuron> neighbours) {
        unitWidth = 0.0;
        double distance;
        for (RadianNeuron neighbour : neighbours) {
            distance = Math.abs(unitCenter - neighbour.getUnitCenter());
            unitWidth += distance * distance;
        }

        unitWidth = Math.sqrt(unitWidth / 2);
    }

    private double gaussianActivation(double x) {
        double distance = Math.abs(x - unitCenter);
        return Math.exp(-((distance * distance) / (unitWidth * unitWidth)));
    }
}
