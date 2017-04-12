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

    public Trainer(MLP mlp, List<List<Double>> inputs, List<List<Double>> expectedOutputs) {
        this.mlp = mlp;
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
        this.graphUtil = new GraphUtil();
    }

    public void train() {

        mlp.setLearningRate(learningRate);
        mlp.setMomentum(momentum);

        List<XYDataItem> dataItems = new ArrayList<>();

        int epoch = 0;
        double cost;
        List<Double> result;
        do {
            epoch++;
            cost = 0.0;
            for (int i = 0; i < inputs.size(); i++) {
                result = mlp.train(
                        inputs.get(i),
                        expectedOutputs.get(i)
                );

                cost += calculateCost(result, expectedOutputs.get(i));
            }

            dataItems.add(new XYDataItem(epoch, cost));

        } while (epoch < maxIterations && cost > eps);

        System.out.println("Trening summary: ");
        System.out.println("Iterations: " + epoch);
        System.out.println("Cost: " + cost);

        graphUtil.printGraph("MSU errors", dataItems, "epoch", "MSU");
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
