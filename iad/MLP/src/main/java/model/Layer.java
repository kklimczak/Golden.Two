package model;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Double> neurons;

    public Layer() {
        neurons = new ArrayList<>();
    }

    public void addBias(boolean ifBias){
        if(ifBias){
            neurons.add(0,1.0);
        } else {
            neurons.add(0,0.0);
        }
    }

    public int getLayerSize(){
        return neurons.size();
    }

    public void addInput(int index, Double value){
        neurons.set(index, value);
    }

    public double getNeuron(int index){
        return neurons.get(index);
    }

    public void setNeuron(int index, Double neuron){
        neurons.set(index, neuron);
    }

    public List<Double> getOutput(){
        return neurons;
    }

    public void setInput(List<Double> input, boolean ifBias) {
        neurons.clear();

        addBias(ifBias);
        for (Double in : input) {
            neurons.add(in);
        }

    }
}
