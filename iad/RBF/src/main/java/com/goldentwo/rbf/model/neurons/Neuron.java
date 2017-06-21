package com.goldentwo.rbf.model.neurons;

import com.goldentwo.rbf.model.other.Point;
import com.goldentwo.utils.AppProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Neuron {
    private List<Double> weights = new ArrayList<>();
    private boolean bias;

    public Neuron(int neuronsInputSize) {
        bias = Boolean.parseBoolean(AppProperties.getProperty("bias"));

        Random random = new Random();
        for (int i = 0; i < neuronsInputSize + 1; i++) {
            weights.add(random.nextDouble() - 0.5);
        }

    }

    public void updateWeights(List<Double> input, Point output, double learningRate) {
        Double neuronOutput = calculateOutput(input);
        double delta = output.getY() - neuronOutput;

        for (int i = 0; i < weights.size() - 1; i++) {
            double updatedWeight = weights.get(i) + (learningRate * delta * input.get(i));
            weights.set(i, updatedWeight);
        }

        if (bias) {
            updateBias(learningRate, delta);
        }
    }

    private void updateBias(double learningRate, double delta) {
        int biasIndex = weights.size() - 1;
        double updatedBiasWeight = weights.get(biasIndex) + (learningRate * delta);
        weights.set(biasIndex, updatedBiasWeight);
    }

    public Double calculateOutput(List<Double> values) {
        Double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i) * weights.get(i);
        }

        return sum;
    }
}
