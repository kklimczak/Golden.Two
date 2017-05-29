package com.goldentwo.model;

import lombok.Data;
import org.jfree.data.xy.XYDataItem;
import com.goldentwo.utils.AppProperties;
import com.goldentwo.utils.GraphUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class Trainer {

    private MLP mlp;
    private List<List<Double>> inputs;
    private List<List<Double>> expectedOutputs;

    private double learningRate;
    private int maxIterations;
    private double eps;
    private double momentum;

    private GraphUtil graphUtil;

    public Trainer(MLP mlp, List<List<Double>> inputs, List<List<Double>> expectedOutputs, double learningRate, double momentum) {
        this.mlp = mlp;
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
        this.graphUtil = new GraphUtil();
        this.learningRate = learningRate;
        this.momentum = momentum;

        this.maxIterations = Integer.parseInt(AppProperties.getProperty("iteration.max"));
        this.eps = Double.parseDouble(AppProperties.getProperty("eps"));
    }

    public void train() {

        prepareLearningParameters();

        System.out.println("Network is training...");
        long before = System.currentTimeMillis();

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

        long after = System.currentTimeMillis();

        System.out.println("Training completed after: " + (after - before) + "ms");

        printTrainingSummary(epoch, MSE);
//        plotMSE(dataItems);                plotting this is useless here
    }

    private void prepareLearningParameters() {
        mlp.setLearningRate(learningRate);
        mlp.setMomentum(momentum);
    }

    private void printTrainingSummary(int epoch, double cost) {
        System.out.println("Trening summary: ");
        System.out.println("Iterations: " + epoch);
        System.out.println("Cost: " + cost);
        System.out.println("Learning rate: " + learningRate);
        System.out.println("Momentum: " + momentum);
    }

//    private void plotMSE(List<XYDataItem> dataItems) {
//        graphUtil.printGraph("MSE error", dataItems, "epoch", "MSE");
//    }

    private double calculateMSE(List<Double> result, List<Double> expectedOutput) {
        Double cost = 0.0, localError;
        for (int i = 0; i < expectedOutput.size(); i++) {
            localError = expectedOutput.get(i) - result.get(i + 1);
            cost += 0.5 * localError * localError;
        }

        return cost;
    }
}
