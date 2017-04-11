package model;

import utils.ActivationFunc;

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

    public List<Double> propagate(List<Double> input) {

        setNetworkInput(input);

        propagateBetweenLayers(inputLayer, hiddenLayer);
        propagateBetweenLayers(hiddenLayer, outputLayer);

        return outputLayer.getOutput();
    }

    List<Double> train(List<Double> pattern, List<Double> expectedOutput, double learningRate) {
        List<Double> output = propagate(pattern);
        backPropagation(expectedOutput, learningRate);

        return output;
    }

    private void backPropagation(List<Double> expectedOutput, double learningRate) {
        resetErrors();

        calculateOutputErrors(expectedOutput);
        calculateHiddenLayerErrors();

        updateNeuronErrors(outputLayer, hiddenLayer, learningRate);
        updateNeuronErrors(hiddenLayer, inputLayer, learningRate);

    }

    private void updateNeuronErrors(Layer layerToUpdate, Layer prevLayer, double learningRate) {
        double newWeight;
        for (int j = 1; j < layerToUpdate.getLayerSize(); j++) {
            for (int i = 0; i < prevLayer.getLayerSize(); i++) {
                newWeight = layerToUpdate.getWeight(j, i) + learningRate * layerToUpdate.getError(j) * prevLayer.getNeuron(i);
                layerToUpdate.setWeight(j, i, newWeight);
            }
        }
    }

    private void calculateHiddenLayerErrors() {
        double neuron;
        double neuronError;
        double errorSum = 0.0;
        for (int i = 1; i < hiddenLayer.getLayerSize(); i++) {
            for (int j = 1; j < outputLayer.getLayerSize(); j++) {
                errorSum += outputLayer.getWeight(j, i) * outputLayer.getError(j);
            }
            neuron = hiddenLayer.getNeuron(i);
            neuronError = neuron * (1.0 - neuron) * errorSum;
            hiddenLayer.addError(neuronError);
            errorSum = 0.0;
        }
    }

    private void calculateOutputErrors(List<Double> expectedOutput) {
        double neuron;
        double neuronError;
        for (int i = 1; i < outputLayer.getLayerSize(); i++) {
            neuron = outputLayer.getNeuron(i);
            neuronError = ActivationFunc.sigmoidDerivative(neuron) * (expectedOutput.get(i - 1) - neuron);
            outputLayer.addError(neuronError);
        }
    }

    private void resetErrors() {
        inputLayer.resetErrors();
        hiddenLayer.resetErrors();
        outputLayer.resetErrors();
    }

    private void propagateBetweenLayers(Layer layerFrom, Layer layerTo) {
        List<Double> output = new ArrayList<>();
        for (int j = 1; j < layerTo.getLayerSize(); j++) {
            double passedValue = 0.0;
            for (int i = 0; i < layerFrom.getLayerSize(); i++) {
                passedValue += layerTo.getWeight(j, i) * layerFrom.getNeuron(i);
            }
            output.add(ActivationFunc.sigmoid(passedValue));
        }
        layerTo.setInput(output);
    }

    private void setNetworkInput(List<Double> pattern) {
        inputLayer.setInput(pattern);
    }

}
