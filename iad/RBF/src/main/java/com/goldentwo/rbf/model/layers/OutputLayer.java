package com.goldentwo.rbf.model.layers;

import com.goldentwo.rbf.model.neurons.Neuron;
import com.goldentwo.rbf.model.other.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputLayer {
    private Neuron neuron;
    private double learningRate;

    public Double getOutput(List<Double> values){
        return neuron.calculateOutput(values);
    }

    public void train(List<Double> input, Point output) {
        neuron.updateWeights(input, output, learningRate);
    }

    public void activateLayer(double learningRate, int hiddenLayerSize){
        this.learningRate = learningRate;
        neuron = new Neuron(hiddenLayerSize);
    }
}
