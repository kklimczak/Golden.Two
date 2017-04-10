package model;

import java.util.ArrayList;
import java.util.List;

public class MLP {
    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    private List<Double> outputValues;

    private Double learningStep = 0.001;
    private int maxIter = 100;
    private Double epsilon = 0.01;

    private boolean ifBias;


    public MLP (int inputLayer, int hiddenLayer, int outputLayer, boolean ifBias){
        createLayers(inputLayer, hiddenLayer, outputLayer, ifBias);
        outputValues = new ArrayList<>();
        this.ifBias = ifBias;
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
                prevLayerSize,
                false
        );
    }

    private Layer createInputLayer(int inputLayer, boolean ifBias) {
        return new Layer(
                inputLayer,
                ifBias,
                1,
                true
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
        outputValues = getLayerOutput(outputLayer);

        this.outputValues = outputValues;
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

    private void calculateErrors(List<Double> expectedValues) {
        calculateOutputErrors(expectedValues);
        calculateHiddenLayerErrors();
    }

    private void backPropagation(){
        updateOutputLayerWeights();
        updateHiddenLayerWeights();
    }

    private void updateHiddenLayerWeights() {
        Neuron neuron;
        Double newWeight;
        int weightIndex = 0;
        for (; weightIndex < inputLayer.getLayerSize(); weightIndex++) {
            for (int neuronIndex = 0; neuronIndex < hiddenLayer.getLayerSize(); neuronIndex++) {
                neuron = hiddenLayer.getNeuron(neuronIndex);
                newWeight =
                        neuron.getWeight(weightIndex) + (
                                learningStep
                                        * neuron.getError()
                                        * neuron.getDiverativeOutput()
                                        * neuron.getInputValue(weightIndex)
                        );
                hiddenLayer.setNeuronWeight(neuronIndex, weightIndex, newWeight);
            }
        }
        if(ifBias){
            updateHiddenBias(weightIndex);
        }
    }

    private void updateHiddenBias(int weightIndex) {
        Neuron neuron;
        Double newWeight;
            for (int neuronIndex = 0; neuronIndex < hiddenLayer.getLayerSize(); neuronIndex++) {
                neuron = hiddenLayer.getNeuron(neuronIndex);
                newWeight =
                        neuron.getWeight(weightIndex) + (
                                learningStep
                                        * neuron.getError()
                                        * neuron.getDiverativeOutput()
                        );
                hiddenLayer.setNeuronWeight(neuronIndex, weightIndex, newWeight);
            }

    }

    private void updateOutputLayerWeights() {
        Double newWeight;
        Neuron neuron;
        int weightIndex = 0;
        for (; weightIndex < hiddenLayer.getLayerSize(); weightIndex++) {
            for (int neuronIndex = 0; neuronIndex < outputLayer.getLayerSize(); neuronIndex++) {
                neuron = outputLayer.getNeuron(neuronIndex);
                newWeight =
                        neuron.getWeight(weightIndex) + (
                                learningStep
                                        * neuron.getError()
                                        * neuron.getDiverativeOutput()
                                        * neuron.getInputValue(weightIndex)
                        );
                outputLayer.setNeuronWeight(neuronIndex, weightIndex, newWeight);
            }
        }
        if(ifBias) {
            updateBias(weightIndex);
        }
    }

    private void updateBias(int weightIndex) {
        Neuron neuron;
        Double newWeight;
        for (int neuronIndex = 0; neuronIndex < outputLayer.getLayerSize(); neuronIndex++) {
            neuron = outputLayer.getNeuron(neuronIndex);
            newWeight =
                    neuron.getWeight(weightIndex) + (
                        learningStep
                                * neuron.getError()
                                * neuron.getDiverativeOutput()
                );
            outputLayer.setNeuronWeight(neuronIndex, weightIndex, newWeight);
        }
    }

    private void calculateHiddenLayerErrors() {
        Double error;
        for (int i = 0; i < hiddenLayer.getLayerSize(); i++) {
            error = 0.0;

            for (int j = 0; j < outputLayer.getLayerSize(); j++) {
                error += outputLayer.getNeuronError(j) * outputLayer.getNeuronWeight(j, i);
            }

            hiddenLayer.setNeuronError(i, error);
        }
    }

    private void calculateOutputErrors(List<Double> expectedValues) {
        Double error;
        for (int i = 0; i < expectedValues.size(); i++) {
            error = expectedValues.get(i) - outputValues.get(i);
            outputLayer.setNeuronError(i, error);
        }
    }

    public void train(List<List<Double>> trainList, List<List<Double>> expectedValues){
        int iteration = 0;
        double cost;
        do {
            iteration++;
            cost = 0;
            for (int epoch = 0; epoch < trainList.size(); epoch++) {
                propagate(trainList.get(epoch));
                calculateErrors(expectedValues.get(epoch));
                backPropagation();

                cost += calculateCosts();
            }
        } while (iteration < maxIter || cost <= epsilon);

        System.out.println("Training completed. Summary:");
        System.out.println("Iterations: " + iteration);
        System.out.println("Costs: " + cost);
    }

    private double calculateCosts() {
        Double error = 0.0;
        for (Neuron neuron : outputLayer.getNeuronList()) {
            Double neuronError = neuron.getError();
            error += 0.5 * neuronError * neuronError;
        }

        return error;
    }

    public List<Double> run(List<Double> input){
        propagate(input);
        return this.outputValues;
    }

}
