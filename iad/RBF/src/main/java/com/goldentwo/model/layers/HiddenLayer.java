package com.goldentwo.model.layers;

import com.goldentwo.model.neurons.RadianNeuron;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiddenLayer {
    private List<RadianNeuron> neurons = new ArrayList<>();

    public List<Double> getLayerOutput(){
        throw new NotImplementedException();
    }
}
