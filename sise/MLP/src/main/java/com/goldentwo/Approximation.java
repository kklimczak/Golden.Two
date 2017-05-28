package com.goldentwo;

import com.goldentwo.model.MLP;
import com.goldentwo.model.Trainer;
import org.jfree.data.xy.XYDataItem;
import com.goldentwo.utils.AppProperties;
import com.goldentwo.utils.GraphUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Approximation {
    private MLP mlp;

    private List<List<Double>> trainList;
    private List<List<Double>> trainResult;
    private double learningRate;
    private double momentum;

    private GraphUtil graphUtil = new GraphUtil();

    private int inputAndOutputSize;

    Approximation() {
        setNetwork();

        this.learningRate = Double.parseDouble(AppProperties.getProperty("learningRate"));
        this.momentum = Double.parseDouble(AppProperties.getProperty("momentum"));
        this.inputAndOutputSize = Integer.parseInt(AppProperties.getProperty("inputOutput.size"));

        loadTrainList();
    }

    private void loadTrainList() {
        Scanner dataScanner = null;
        Scanner resultScanner = null;
        try {
            dataScanner = new Scanner(new File("build/resources/main/data/approx.txt"));
            resultScanner = new Scanner(new File("build/resources/main/data/result.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<List<Double>> dataOutput = new ArrayList<>();
        List<List<Double>> resultOutput = new ArrayList<>();
        List<Double> dataRows, outputRows;
        int iterator = 0;
        while (dataScanner != null && dataScanner.hasNext()) {
            dataRows = new ArrayList<>();
            outputRows = new ArrayList<>();
            do {
                dataRows.add(dataScanner.nextDouble());
                outputRows.add(resultScanner.nextDouble());
                iterator++;
            } while (iterator % inputAndOutputSize != 0);
            dataOutput.add(dataRows);
            resultOutput.add(outputRows);
        }
        if (dataScanner != null) {
            dataScanner.close();
            resultScanner.close();
        }

        this.trainList = dataOutput;
        this.trainResult = resultOutput;
    }

    void train() {
        Trainer trainer = new Trainer(mlp, trainList, trainResult, learningRate, momentum);
        trainer.train();

        printNetworkLearningResults();
    }

    private void printNetworkLearningResults() {
        List<XYDataItem> dataItems = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            dataItems.add(new XYDataItem(new Double(i), mlp.propagate(Collections.singletonList(i * 1.0)).get(1)));
        }

        graphUtil.printGraph("Learning results", dataItems, "number", "root square");
    }

    private void setNetwork() {
        int hiddenLayerSize = Integer.parseInt(AppProperties.getProperty("hiddenLayerSize"));

        mlp = new MLP(inputAndOutputSize, hiddenLayerSize, inputAndOutputSize, false);
    }
}
