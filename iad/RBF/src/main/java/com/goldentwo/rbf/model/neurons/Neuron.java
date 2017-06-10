package com.goldentwo.rbf.model.neurons;

import com.goldentwo.rbf.model.other.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Neuron {
    private List<Double> weights = new ArrayList<>();

    public Neuron(int neuronsInputSize) {
        Random random = new Random();
        for (int i = 0; i < neuronsInputSize; i++) {
            weights.add(random.nextDouble() - 0.5);
        }
    }

    public void updateWeights(List<Double> input, Point output, double learningRate){
        Double neuronOutput = calculateOutput(input);
        for (int i = 0; i < weights.size(); i++) {
            double delta = output.getY() - neuronOutput;
            double updatedWeight = weights.get(i) + (learningRate * delta * input.get(i));
            weights.set(i, updatedWeight);
        }
    }

    public Double calculateOutput(List<Double> values) {
        Double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i) * weights.get(i);
        }

        return sum;
    }
}
