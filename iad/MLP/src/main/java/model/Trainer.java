package model;

import lombok.Data;
import org.jfree.data.xy.XYDataItem;
import utils.GraphUtil;

import java.util.ArrayList;
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

    private GraphUtil graphUtil;

    public Trainer(MLP mlp, List<List<Double>> inputs, List<List<Double>> expectedOutputs, double learningRate, double momentum) {
        this.mlp = mlp;
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
        this.graphUtil = new GraphUtil();
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    public void train() {

        prepareLearningParameters();

        double MSE;
        int epoch = 0;
        List<Double> result;
        List<XYDataItem> dataItems = new ArrayList<>();
        do {
            epoch++;
            MSE = 0.0;
            for (int i = 0; i < inputs.size(); i++) {
                result = mlp.train(
                        inputs.get(i),
                        expectedOutputs.get(i)
                );
                MSE += calculateMSE(result, expectedOutputs.get(i));
            }
            dataItems.add(new XYDataItem(epoch, MSE));
        } while (epoch < maxIterations && MSE > eps);

        printTrainingSummary(epoch, MSE);
        plotMSE(dataItems);
    }

    private void prepareLearningParameters() {
        mlp.setLearningRate(learningRate);
        mlp.setMomentum(momentum);
    }

    private void printTrainingSummary(int epoch, double cost) {
        System.out.println("Trening summary: ");
        System.out.println("Iterations: " + epoch);
        System.out.println("Cost: " + cost);
    }

    private void plotMSE(List<XYDataItem> dataItems) {
        graphUtil.printGraph("MSE error", dataItems, "epoch", "MSE");
    }

    private double calculateMSE(List<Double> result, List<Double> expectedOutput) {
        Double cost = 0.0, localError;
        for (int i = 0; i < expectedOutput.size(); i++) {
            localError = expectedOutput.get(i) - result.get(i + 1);
            cost += 0.5 * localError * localError;
        }

        return cost;
    }
}
