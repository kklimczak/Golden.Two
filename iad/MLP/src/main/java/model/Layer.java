package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Layer {
    private List<Neuron> neuronList;
    private boolean ifInputLayer;

    public Layer(int layerSize, List<Double> inputValues, boolean ifBias, int prevLayerSize){
        neuronList = new ArrayList<>();
        if(prevLayerSize == 1){
            ifInputLayer = true;
            neuronList = prepareInputLayer(layerSize, inputValues, ifBias);
        } else {
            ifInputLayer = false;
            neuronList = prepareLayer(layerSize, inputValues, ifBias, prevLayerSize);
        }
    }

    public Layer(int layerSize, boolean ifBias, int prevLayerSize){
        ifInputLayer = false;
        neuronList = new ArrayList<>();
        neuronList = prepareLayer(layerSize, ifBias, prevLayerSize);
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

    private List<Neuron> prepareLayer(int layerSize, List<Double> inputValues, boolean ifBias, int prevLayerSize) {
        List<Neuron> output = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            output.add(
                    new Neuron(
                            inputValues,
                            ifBias,
                            prevLayerSize
                    )
            );
        }
        return output;
    }

    private List<Neuron> prepareInputLayer(int layerSize, List<Double> inputValues, boolean ifBias) {
        List<Neuron> output = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            output.add(
                    new Neuron(
                            Collections.singletonList(inputValues.get(i)),
                            ifBias,
                            1)
            );
        }
        return output;
    }

    public int getLayerSize(){
        return neuronList.size();
    }

    public Double getOutput(int neuronNumb){
        if(ifInputLayer){
            return neuronList.get(neuronNumb).duplicateInput();
        }
        return neuronList.get(neuronNumb).getOutput();
    }

    public void setInput(List<Double> inputValues){
        for (Neuron neuron : neuronList) {
            neuron.setInputValues(inputValues);
        }
    }

}
