package model;

import utils.ActivationFunc;
import utils.MyUtil;

import java.util.ArrayList;
import java.util.List;


public class MLP {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    private int inputLayerSize;
    private int hiddenLayerSize;
    private int outputLayerSize;

    private boolean ifBias;

    private double [][] weightL1, weigthL2;


    public MLP (int inputNeurons, int hiddenNeurons, int outputNeurons, boolean ifBias){
        inputLayer = new Layer();
        hiddenLayer = new Layer();
        outputLayer = new Layer();

        this.inputLayerSize = inputNeurons;
        this.hiddenLayerSize = hiddenNeurons;
        this.outputLayerSize = outputNeurons;

        this.ifBias = ifBias;

        weightL1 = new double[hiddenNeurons+1][inputNeurons+1];
        weigthL2 = new double[outputNeurons+1][hiddenNeurons+1];

        generateRandomWeights();
    }


    private void generateRandomWeights() {

        for(int j=1; j<=hiddenLayerSize; j++)
            for(int i=0; i<=inputLayerSize; i++) {
                weightL1[j][i] = MyUtil.randomNumber(-0.5, 0.5);
            }

        for(int j=1; j<=outputLayerSize; j++)
            for(int i=0; i<=hiddenLayerSize; i++) {
                weigthL2[j][i] = MyUtil.randomNumber(-0.5, 0.5);
            }
    }

    public List<Double> train(List<Double> pattern, List<Double> desiredOutput, double learningRate) {
        List<Double> output = propagate(pattern);
        backpropagation(desiredOutput, learningRate);

        return output;
    }

    private void backpropagation(List<Double> desiredOutput, double learningRate) {
        double[] errorL2 = new double[outputLayer.getLayerSize()];
        double[] errorL1 = new double[hiddenLayer.getLayerSize()];
        double Esum = 0.0;

        for(int i=1; i < outputLayer.getLayerSize(); i++) { // Layer 2 error gradient
            Double neuron = outputLayer.getNeuron(i);
            errorL2[i] = neuron * (1.0 - neuron) * (desiredOutput.get(i-1) - neuron);
        }


        for(int i=0; i < hiddenLayer.getLayerSize(); i++) {  // Layer 1 error gradient
            for(int j=1; j < outputLayer.getLayerSize(); j++)
                Esum += weigthL2[j][i] * errorL2[j];

            errorL1[i] = hiddenLayer.getNeuron(i) * (1.0-hiddenLayer.getNeuron(i)) * Esum;
            Esum = 0.0;
        }

        for(int j=1; j<outputLayer.getLayerSize(); j++)
            for(int i=0; i<hiddenLayer.getLayerSize(); i++)
                weigthL2[j][i] += learningRate * errorL2[j] * hiddenLayer.getNeuron(i);

        for(int j=1; j<hiddenLayer.getLayerSize(); j++)
            for(int i=0; i<inputLayer.getLayerSize(); i++)
                weightL1[j][i] += learningRate * errorL1[j] * inputLayer.getNeuron(i);

    }

    public List<Double> propagate(List<Double> pattern) {
        inputLayer.setInput(pattern, ifBias);
        List<Double> output = new ArrayList<>();

        // Passing through hidden layer
        for(int j=1; j<= hiddenLayerSize; j++) {
            double passedValue = 0.0;
            for(int i=0; i<inputLayer.getLayerSize(); i++) {
                passedValue += weightL1[j][i] * inputLayer.getNeuron(i);
            }
            output.add(ActivationFunc.sigmoid(passedValue));
        }
        hiddenLayer.setInput(output, ifBias);

        // Passing through output layer
        output.clear();
        for(int j=1; j<= outputLayerSize; j++) {
            double passedValue = 0.0;
            for(int i=0; i<hiddenLayer.getLayerSize(); i++) {
                passedValue += weigthL2[j][i] * hiddenLayer.getNeuron(i);
            }
            output.add(ActivationFunc.sigmoid(passedValue));
        }
        outputLayer.setInput(output, ifBias);

        return outputLayer.getOutput();
    }

}
