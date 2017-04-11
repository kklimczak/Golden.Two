import model.MLP;
import model.Trainer;

import java.util.Arrays;
import java.util.List;

class Application {
    public static void main(String[] args) {

        MLP newHope = new MLP(
                4,
                2,
                4,
                true
        );

        List<List<Double>> trainList = prepareTrainingSet();
        Trainer trainer = new Trainer(
                newHope,
                trainList,
                trainList
        );

        trainer.train();

        //AFTER TRAINING TESTS
        for (List<Double> example : trainList) {
            newHope.propagate(example)
                    .stream()
                    .filter(n -> n != 1.0)
                    .map(Math::round)
                    .map(n -> n + " ")
                    .forEach(System.out::print);
            System.out.println();
        }

    }

    private static List<List<Double>> prepareTrainingSet() {
        List<Double> l1 = Arrays.asList(1.0, 0.0, 0.0, 0.0);
        List<Double> l2 = Arrays.asList(0.0, 1.0, 0.0, 0.0);
        List<Double> l3 = Arrays.asList(0.0, 0.0, 1.0, 0.0);
        List<Double> l4 = Arrays.asList(0.0, 0.0, 0.0, 1.0);

        return Arrays.asList(l1, l2, l3, l4);
    }
}