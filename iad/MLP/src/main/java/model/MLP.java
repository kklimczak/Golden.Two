package model;

import utils.ActivationFunc;
import utils.MyUtil;

import java.util.ArrayList;
import java.util.List;


public class MLP {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    public MLP(int inputNeurons, int hiddenNeurons, int outputNeurons, boolean ifBias) {
        inputLayer = new Layer(inputNeurons, ifBias, 1);
        hiddenLayer = new Layer(hiddenNeurons, ifBias, inputNeurons);
        outputLayer = new Layer(outputNeurons, ifBias, hiddenNeurons);

    }

    List<Double> train(List<Double> pattern, List<Double> desiredOutput, double learningRate) {
        List<Double> output = propagate(pattern);
        backPropagation(desiredOutput, learningRate);

        return output;
    }

    private void backPropagation(List<Double> desiredOutput, double learningRate) {
        double[] errorL2 = new double[outputLayer.getLayerSize()];
        double[] errorL1 = new double[hiddenLayer.getLayerSize()];
        double Esum = 0.0;
        double newWeigth;

        for (int i = 1; i < outputLayer.getLayerSize(); i++) { // Layer 2 error gradient
            Double neuron = outputLayer.getNeuron(i);
            errorL2[i] = neuron * (1.0 - neuron) * (desiredOutput.get(i - 1) - neuron);
        }


        for (int i = 0; i < hiddenLayer.getLayerSize(); i++) {  // Layer 1 error gradient
            for (int j = 1; j < outputLayer.getLayerSize(); j++)
                Esum += outputLayer.getWeight(j, i) * errorL2[j];

            errorL1[i] = hiddenLayer.getNeuron(i) * (1.0 - hiddenLayer.getNeuron(i)) * Esum;
            Esum = 0.0;
        }

        for (int j = 1; j < outputLayer.getLayerSize(); j++)
            for (int i = 0; i < hiddenLayer.getLayerSize(); i++) {
                newWeigth = outputLayer.getWeight(j, i) + learningRate * errorL2[j] * hiddenLayer.getNeuron(i);
                outputLayer.setWeight(j, i, newWeigth);
            }

        for (int j = 1; j < hiddenLayer.getLayerSize(); j++)
            for (int i = 0; i < inputLayer.getLayerSize(); i++) {
                newWeigth = hiddenLayer.getWeight(j, i) + learningRate * errorL1[j] * inputLayer.getNeuron(i);
                hiddenLayer.setWeight(j, i, newWeigth);
            }
    }

    public List<Double> propagate(List<Double> pattern) {
        inputLayer.setInput(pattern);
        List<Double> output = new ArrayList<>();

        // Passing through hidden layer
        for (int j = 1; j < hiddenLayer.getLayerSize(); j++) {
            double passedValue = 0.0;
            for (int i = 0; i < inputLayer.getLayerSize(); i++) {
                passedValue += hiddenLayer.getWeight(j, i) * inputLayer.getNeuron(i);
            }
            output.add(ActivationFunc.sigmoid(passedValue));
        }
        hiddenLayer.setInput(output);

        // Passing through output layer
        output.clear();
        for (int j = 1; j < outputLayer.getLayerSize(); j++) {
            double passedValue = 0.0;
            for (int i = 0; i < hiddenLayer.getLayerSize(); i++) {
                passedValue += outputLayer.getWeight(j, i) * hiddenLayer.getNeuron(i);
            }
            output.add(ActivationFunc.sigmoid(passedValue));
        }
        outputLayer.setInput(output);

        return outputLayer.getOutput();
    }

}
