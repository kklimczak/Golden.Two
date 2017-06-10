package com.goldentwo.rbf.model.layers;

import com.goldentwo.rbf.model.neurons.Neuron;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputLayer {
    private Neuron neuron;

    public Double getOutput(List<Double> values){
        return neuron.calculateOutput(values);
    }
}
