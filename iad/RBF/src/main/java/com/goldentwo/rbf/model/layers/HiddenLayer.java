package com.goldentwo.rbf.model.layers;

import com.goldentwo.rbf.model.neurons.RadianNeuron;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiddenLayer {
    private List<RadianNeuron> neurons = new ArrayList<>();

    public List<Double> calculateOutput(double input) {
        List<Double> output = new ArrayList<>();
        for (RadianNeuron neuron : neurons) {
            output.add(neuron.calculateOutput(input));
        }

        return output;
    }

    public void addNeuron(RadianNeuron radianNeuron) {
        neurons.add(radianNeuron);
    }

    public void calculateSigmas() {
        List<RadianNeuron> distanceSorted = new ArrayList<>(neurons);
        for (RadianNeuron radianNeuron : neurons) {
            distanceSorted.sort((rn1, rn2) -> (int) (dist(rn1, radianNeuron) - dist(rn2, radianNeuron)));
            radianNeuron.calculateUnitWidth(distanceSorted.subList(1, 3));
        }
    }

    private double dist(RadianNeuron from, RadianNeuron to) {
        return Math.abs(from.getUnitCenter() - to.getUnitCenter());
    }
}
