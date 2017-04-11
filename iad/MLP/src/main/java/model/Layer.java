package model;

import java.util.ArrayList;
import java.util.List;

class Layer {
    private List<Double> neurons;
    private int layerSize;
    private boolean ifBias;

    Layer(int layerSize, boolean ifBias) {
        neurons = new ArrayList<>();
        this.ifBias = ifBias;
        this.layerSize = layerSize + 1; // + 1 for bias
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
