package model;

import utils.ActivationFunc;
import utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private List<Double> inputValues;
    private List<Double> weights;

    private boolean ifBias;

    public Neuron(List<Double> inputValues, boolean bias, int inputSize){
        this.inputValues = inputValues;
        this.weights = new ArrayList<>(inputSize);

        generateWeights(inputSize);
        addBias(bias);
    }

    public Neuron(boolean bias, int inputSize){
        this.inputValues = new ArrayList<>();
        this.weights = new ArrayList<>(inputSize);

        generateWeights(inputSize);
        addBias(bias);
    }

    public double getOutput(){
        return ActivationFunc.sigmoid(calculateOutput());
    }

    public void setInputValues(List<Double> inputValues){
        this.inputValues = inputValues;
    }

    public void updateWeight(int index, Double value){
        weights.set(index, value);
    }

    private void generateWeights(int inputSize) {
        for (int i = 0; i < inputSize; i++) {
            weights.add(generateWeight());
        }
    }

    private void addBias(boolean bias) {
        if(bias){
            this.weights.add(generateWeight());
        }
        this.ifBias = bias;
    }

    private Double generateWeight() {
        return MyUtil.randomNumber(0,1);
    }

    private double calculateOutput(){
        Double sum = 0.0;
        int i = 0;
        for (; i < inputValues.size(); i++) {
            sum += inputValues.get(i) * weights.get(i);
        }
        if(ifBias){
            sum += weights.get(i);
        }

        return sum;
    }

    public double duplicateInput(){
        return inputValues.get(0);
    }
}
