package model;

import utils.ActivationFunc;
import utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private List<Double> inputValues;
    private List<Double> weights;
    private Double error;

    private boolean ifBias;

    public Neuron(boolean bias, int inputSize){
        this.inputValues = new ArrayList<>();
        this.weights = new ArrayList<>(inputSize);

        generateWeights(inputSize);
        addBias(bias);
    }

    public void addInput(Double input){
        inputValues.add(input);
    }

    public double getOutput(){
        return ActivationFunc.sigmoid(calculateOutput());
    }

    public double getDiverativeOutput(){
        return ActivationFunc.sigmoidDerivative(calculateOutput());
    }

    public void setInputValues(List<Double> inputValues){
        this.inputValues = inputValues;
    }

    public void updateWeight(int index, Double value){
        weights.set(index, value);
    }

    public List<Double> getWeights(){
        return weights;
    }

    public Double getWeight(int index){
        return weights.get(index);
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

    public Double getError(){
        return error;
    }

    public void setError(Double error){
        this.error = error;
    }

    public Double getInputValue(int index){
        return inputValues.get(index);
    }
}
