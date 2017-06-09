package com.goldentwo.model.neurons;

import lombok.Data;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public void updateWeights(){
        throw new NotImplementedException();
    }

    public Double calculateOutput(List<Double> values) {
        Double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i) * weights.get(i);
        }

        return sum;
    }
}
