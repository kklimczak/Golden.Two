import examples.MLP2;
import model.MLP;

import java.util.Arrays;
import java.util.List;

class Application {
    public static void main(String[] args) {
//
//        List<Double> l1 = Arrays.asList(1.0, 0.0, 0.0, 0.0);
//        List<Double> l2 = Arrays.asList(0.0, 1.0, 0.0, 0.0);
//        List<Double> l3 = Arrays.asList(0.0, 0.0, 1.0, 0.0);
//        List<Double> l4 = Arrays.asList(0.0, 0.0, 0.0, 1.0);
//
//        List<List<Double>> trainingSet = Arrays.asList( l1, l2, l3, l4 );
//
//        MLP mlp = new MLP(4, 2, 4, true);
//        mlp.train(trainingSet,trainingSet);
//
//        System.out.println(mlp.run(l1));
//        System.out.println(mlp.run(l2));
//        System.out.println(mlp.run(l3));
//        System.out.println(mlp.run(l4));

        MLP2 mlp2 = new MLP2(4,2,4);
        double[] t1 = {1.0, 0.0, 0.0, 0.0};
        double[] t2 = {0.0, 1.0, 0.0, 0.0};
        double[] t3 = {0.0, 0.0, 1.0, 0.0};
        double[] t4 = {0.0, 0.0, 0.0, 1.0};

        for (int i = 0; i < 5000; i++) {
            mlp2.train(t1, t1);
            mlp2.train(t2, t2);
            mlp2.train(t3, t3);
            mlp2.train(t4, t4);
        }

        System.out.println(Arrays.toString(mlp2.passNet(Arrays.stream(t1).map(Math::round).toArray())));
        System.out.println(Arrays.toString(mlp2.passNet(t2)));
        System.out.println(Arrays.toString(mlp2.passNet(t3)));
        System.out.println(Arrays.toString(mlp2.passNet(t4)));
    }

}