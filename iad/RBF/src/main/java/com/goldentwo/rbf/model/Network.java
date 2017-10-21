package com.goldentwo.rbf.model;

import com.goldentwo.kmeans.KMeans;
import com.goldentwo.rbf.graph.GraphPlot;
import com.goldentwo.rbf.graph.GraphStyle;
import com.goldentwo.rbf.model.layers.HiddenLayer;
import com.goldentwo.rbf.model.layers.InputLayer;
import com.goldentwo.rbf.model.layers.OutputLayer;
import com.goldentwo.rbf.model.neurons.RadianNeuron;
import com.goldentwo.rbf.model.other.Point;
import com.goldentwo.rbf.utils.FileUtil;
import com.goldentwo.utils.AppProperties;
import lombok.Data;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Network {
    private InputLayer inputLayer = new InputLayer();
    private HiddenLayer hiddenLayer = new HiddenLayer();
    private OutputLayer outputLayer = new OutputLayer();

    private List<Double> errors = new ArrayList<>();
    private double epsilon;

    private KMeans kMeans;

    public Network() {
        String trainingSetFile;

        if (AppProperties.getProperty("training.set").equals("easy")) {
            trainingSetFile = "approximation_train_easy.txt";
        } else {
            trainingSetFile = "approximation_train.txt";
        }

        inputLayer.setPoints(FileUtil.loadPointsFromFile(trainingSetFile));

        int hiddenLayerSize = Integer.parseInt(AppProperties.getProperty("hidden.size"));

        epsilon = Double.parseDouble(AppProperties.getProperty("epsilon"));
        kMeans = new KMeans(inputLayer.getPoints(), hiddenLayerSize, 5);
    }

    public void run() {
        int epoch = Integer.parseInt(AppProperties.getProperty("epoch"));
        double learningRate = Double.parseDouble(AppProperties.getProperty("learning.rate"));

        trainHiddenLayer();
        trainOutputLayer(epoch, learningRate);

        System.out.println("Training completed.");
    }

    public double propagate(double input) {
        return outputLayer.getOutput(hiddenLayer.calculateOutput(input));
    }

    private void trainHiddenLayer() {
        kMeans.run();
        List<Point> calculatedCenters = kMeans.getCalculaterdClustersCenters();

        for (int i = 0; i < kMeans.getClustersNumb(); i++) {
            hiddenLayer.addNeuron(new RadianNeuron(calculatedCenters.get(i).getX()));
        }

        hiddenLayer.calculateSigmas();
    }

    private void trainOutputLayer(int epoch, double learningRate) {
        List<Point> points = new ArrayList<>(inputLayer.getPoints());
        outputLayer.activateLayer(learningRate, hiddenLayer.getNeurons().size());

        double error = 10;
        for (int i = 0; i < epoch && error > epsilon; i++) {
            error = 0;
//            Collections.shuffle(points);
            for (Point point : points) {
                outputLayer.train(hiddenLayer.calculateOutput(point.getX()), point);
                double outputDelta = point.getY() - propagate(point.getX());
                error += outputDelta * outputDelta;
            }
            error /= points.size();
            errors.add(error);
        }
    }

    public void plotResult() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries centers = new XYSeries("Centers");
        XYSeries trainingSeries = new XYSeries("Training set");
        XYSeries approximation = new XYSeries("Approximation");

        for (Point center : kMeans.getCalculaterdClustersCenters()) {
            centers.add(center.getX(), center.getY());
        }

        for (Point point : inputLayer.getPoints()) {
            trainingSeries.add(point.getX(), point.getY());
        }

        double step = 8 / 1000.0;
        double value = -4.0;
        for (int i = 0; i < 1000; i++) {
            approximation.add(value, propagate(value));
            value += step;
        }


        result.addSeries(centers);
        result.addSeries(trainingSeries);
        result.addSeries(approximation);

        GraphPlot plot = new GraphPlot("Network results", result, GraphStyle.SCATTER);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }

    public void plotError() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries errorSeries = new XYSeries("MSE");

        for (int i = 0; i < errors.size(); i++) {
            errorSeries.add(i, errors.get(i));
        }

        result.addSeries(errorSeries);

        GraphPlot plot = new GraphPlot("MSE", result, GraphStyle.LINE);

        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);
    }
}
