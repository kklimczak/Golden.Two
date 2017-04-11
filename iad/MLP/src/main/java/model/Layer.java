package model;

import utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

class Layer {
    private List<Double> neurons;
    private List<List<Double>> weights;
    private List<Double> errors;
    private int layerSize;
    private boolean ifBias;

    Layer(int layerSize, boolean ifBias, int prevLayerSize) {
        neurons = new ArrayList<>();
        weights = new ArrayList<>();
        errors = new ArrayList<>();
        this.ifBias = ifBias;
        this.layerSize = layerSize + 1; // + 1 for bias

        generateWeights(prevLayerSize);
    }

    void resetErrors() {
        errors.clear();
        errors.add(0.0); // dummy bias error
    }

    void addError(Double error) {
        errors.add(error);
    }

    double getError(int errorIndex) {
        return errors.get(errorIndex);
    }

    private void generateWeights(int prevLayerSize) {
        List<Double> weights = new ArrayList<>();
        this.weights.add(weights);
        for (int i = 1; i < this.layerSize; i++) {
            weights = new ArrayList<>();
            for (int j = 0; j < prevLayerSize + 1; j++) {
                weights.add(MyUtil.randomNumber(-0.5, 0.5));
            }
            this.weights.add(weights);
        }
    }

    double getWeight(int neuronIndex, int weightIndex) {
        return weights.get(neuronIndex).get(weightIndex);
    }

    void setWeight(int neuronIndex, int weightIndex, double newWeight) {
        weights.get(neuronIndex).set(weightIndex, newWeight);
    }

    private void addBias() {
        if (ifBias) {
            neurons.add(1.0);
        } else {
            neurons.add(0.0);
        }
    }

    int getLayerSize() {
        return layerSize;
    }

    double getNeuron(int index) {
        return neurons.get(index);
    }

    List<Double> getOutput() {
        return neurons;
    }

    void setInput(List<Double> input) {
        neurons.clear();
        addBias();

        for (Double in : input) {
            neurons.add(in);
        }
    }
}
