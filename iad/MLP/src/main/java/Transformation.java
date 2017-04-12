import model.MLP;
import model.Trainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transformation {
    private MLP[] networks;
    private MLP mlp;

    private List<List<Double>> trainList;

    Transformation(int networkNumb) {
        createNetworks();
        setNetwork(networkNumb);

        trainList = loadTrainList();
    }

    private List<List<Double>> loadTrainList() {
        Scanner s = null;
        try {
            s = new Scanner(new File("build/resources/main/data/transformation.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<List<Double>> output = new ArrayList<>();
        List<Double> list;
        int iterator = 0;
        while (s != null && s.hasNext()) {
            list = new ArrayList<>();
            do {
                list.add(s.nextDouble());
                iterator++;
            } while (iterator % 4 != 0);
            output.add(list);
        }
        if (s != null) {
            s.close();
        }

        return output;
    }

    private void createNetworks() {
        networks = new MLP[6];

        networks[0] = new MLP(4, 1, 4, true);
        networks[1] = new MLP(4, 2, 4, true);
        networks[2] = new MLP(4, 3, 4, true);

        networks[3] = new MLP(4, 1, 4, false);
        networks[4] = new MLP(4, 2, 4, false);
        networks[5] = new MLP(4, 3, 4, false);
    }

    void setMlp(MLP mlp) {
        this.mlp = mlp;
    }

    void train() {
        Trainer trainer = new Trainer(mlp, trainList, trainList);
        trainer.train();

        simpleTreningCheck(trainList);
    }

    private void simpleTreningCheck(List<List<Double>> trainList) {
        for (List<Double> example : trainList) {
            mlp.propagate(example)
                    .stream()
                    .filter(n -> n != 1.0 && n != 0.0)
                    .map(Math::round)
                    .map(n -> n + " ")
                    .forEach(System.out::print);
            System.out.println();
        }
    }

    private void setNetwork(int network) {
        mlp = networks[network - 1];
    }
}
