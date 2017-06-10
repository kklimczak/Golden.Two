package com.goldentwo.rbf.model.neurons;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RadianNeuron {
    private double unitCenter;
    private double unitWidth;

    public double calculateOutput(double x) {
        return gaussianActivation(x);
    }

    private double gaussianActivation(double x) {
        return Math.exp(-(((x - unitCenter) * (x - unitCenter)) / (unitWidth * unitWidth)));
    }
}
