package model;

import lombok.Data;

import java.util.List;

@Data
public class Trainer {

    private MLP mlp;
    private List<List<Double>> inputs;
    private List<List<Double>> expectedOutputs;

    private double learningRate = 0.1;
    private int maxIterations = 10000;
    private double eps = 0.01;
    private double momentum = 0.5;

    public Trainer(MLP mlp, List<List<Double>> inputs, List<List<Double>> expectedOutputs) {
        this.mlp = mlp;
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
    }

    public void train() {

        mlp.setLearningRate(learningRate);
        mlp.setMomentum(momentum);

        int iterator = 0;
        double cost;
        List<Double> result;
        do {
            iterator++;
            cost = 0.0;
            for (int epoch = 0; epoch < inputs.size(); epoch++) {
                result = mlp.train(
                        inputs.get(epoch),
                        expectedOutputs.get(epoch)
                );

                cost += calculateCost(result, expectedOutputs.get(epoch));
            }

        } while (iterator < maxIterations && cost > eps);

        System.out.println("Trening summary: ");
        System.out.println("Iterations: " + iterator);
        System.out.println("Cost: " + cost);
    }

    private double calculateCost(List<Double> result, List<Double> expectedOutput) {
        Double cost = 0.0, localError;
        for (int i = 0; i < expectedOutput.size(); i++) {
            localError = expectedOutput.get(i) - result.get(i + 1);
            cost += 0.5 * localError * localError;
        }

        return cost;
    }
}
