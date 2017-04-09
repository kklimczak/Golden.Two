package model;

import java.util.ArrayList;
import java.util.List;

public class MLP {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    public MLP (int inputLayer, int hiddenLayer, int outputLayer, boolean ifBias){
        createLayers(inputLayer, hiddenLayer, outputLayer, ifBias);
    }

    private void createLayers(int inputLayer, int hiddenLayer, int outputLayer, boolean ifBias) {
        this.inputLayer = createInputLayer(inputLayer, ifBias);
        this.hiddenLayer = createLayer(hiddenLayer, ifBias, inputLayer);
        this.outputLayer = createLayer(outputLayer, ifBias, hiddenLayer);
    }


    private Layer createLayer(int hiddenLayer, boolean ifBias, int prevLayerSize) {
        return new Layer(
                hiddenLayer,
                ifBias,
                prevLayerSize
        );
    }

    private Layer createInputLayer(int inputLayer, boolean ifBias) {
        return new Layer(
                inputLayer,
                ifBias,
                1
        );
    }

    public void setInput(List<Double> input){
        inputLayer.setInput(input);
    }

    public void propagate(List<Double> input){
        setInput(input);
        List<Double> outputValues;
        outputValues = getInputLayerOutput();

        hiddenLayer.setInput(outputValues);
        outputValues = getLayerOutput(hiddenLayer);

        outputLayer.setInput(outputValues);
    }

    private List<Double> getLayerOutput(Layer layer) {
        List<Double> output = new ArrayList<>();
        for (int i = 0; i < layer.getLayerSize(); i++) {
            output.add(layer.getOutput(i));
        }

        return output;
    }

    private List<Double> getInputLayerOutput() {
        List<Double> output = new ArrayList<>();
        for (int i = 0; i < inputLayer.getLayerSize(); i++) {
            output.add(inputLayer.getOutput(i));
        }

        return output;
    }

    public void train(List<List<Double>> trainList, List<List<Double>> expectedValues){

    }

    public void run(List<Double> input){

    }

}
