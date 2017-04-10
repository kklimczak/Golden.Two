package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Layer {
    private List<Neuron> neuronList;
    private boolean ifInputLayer;

    public Layer(int layerSize, boolean ifBias, int prevLayerSize, boolean ifInputLayer){
        neuronList = new ArrayList<>();
        this.ifInputLayer = ifInputLayer;
        if(ifInputLayer){
            neuronList = prepareInputLayer(layerSize, ifBias);
        } else {
            neuronList = prepareLayer(layerSize, ifBias, prevLayerSize);
        }
    }

    private List<Neuron> prepareLayer(int layerSize, boolean ifBias, int prevLayerSize) {
        List<Neuron> output = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            output.add(
                    new Neuron(
                            ifBias,
                            prevLayerSize
                    )
            );
        }
        return output;
    }

    private List<Neuron> prepareInputLayer(int layerSize, boolean ifBias) {
        List<Neuron> output = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            output.add(
                    new Neuron(
                            ifBias,
                            1)
            );
        }
        return output;
    }

    public int getLayerSize(){
        return neuronList.size();
    }

    public List<Neuron> getNeuronList(){
        return neuronList;
    }

    public Double getNeuronWeight(int neuronIndex, int weightIndex){
        return neuronList.get(neuronIndex).getWeight(weightIndex);
    }

    public void setNeuronWeight(int neuronIndex, int weightIndex, Double weightValue){
        Neuron neuron = neuronList.get(neuronIndex);
        neuron.updateWeight(weightIndex, weightValue);
        neuronList.set(neuronIndex, neuron);
    }

    public Double getNeuronError(int neuronIndex){
        return neuronList.get(neuronIndex).getError();
    }

    public void setNeuronError(int neuronIndex, Double error){
        Neuron neuron = neuronList.get(neuronIndex);
        neuron.setError(error);
        neuronList.set(neuronIndex, neuron);
    }

    public Double getOutput(int neuronNumb){
        if(ifInputLayer){
            return neuronList.get(neuronNumb).duplicateInput();
        }
        return neuronList.get(neuronNumb).getOutput();
    }

    public void setInput(List<Double> inputValues){
        int iteration = 0;
        if(ifInputLayer){
            for (Neuron neuron : neuronList) {
                neuron.addInput(inputValues.get(iteration));
                iteration++;
            }
        } else {
            for (Neuron neuron : neuronList) {
                neuron.setInputValues(inputValues);
            }
        }
    }

    public Neuron getNeuron(int index){
        return neuronList.get(index);
    }

}
