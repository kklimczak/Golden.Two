import examples.Perceptron;
import model.Neuron;
import utils.MyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.round;

class Application {
    public static void main(String[] args) {

        List<List<Double>> trainingSet = new ArrayList<>();
        List<Double> trainingOutput = new ArrayList<>();

    }

    private static List<Double> generateClassOne() {
        List<Double> output = new ArrayList<>();
        output.add(MyUtil.randomNumber(-1,0));
        output.add(MyUtil.randomNumber(-1,0));

        return output;
    }

    private static List<Double> generateClassZero() {
        List<Double> output = new ArrayList<>();
        output.add(MyUtil.randomNumber(0.1, 1));
        output.add(MyUtil.randomNumber(0.1, 1));

        return output;
    }


    private static void prepareTraining(List<List<Double>> trainingSet, List<Double> trainingOutput) {
        for (int i = 0; i < 50; i++) {
            List<Double> listElement = Arrays.asList(
                    MyUtil.randomNumber(-1, 0),
                    MyUtil.randomNumber(-1, 0)
            );
            trainingSet.add(listElement);
            trainingOutput.add(1.0);
        }
        for (int i = 0; i < 50; i++) {
            List<Double> listElement = Arrays.asList(
                    MyUtil.randomNumber(0.01, 1),
                    MyUtil.randomNumber(0.01, 1)
            );
            trainingSet.add(listElement);
            trainingOutput.add(0.0);
        }
    }
}