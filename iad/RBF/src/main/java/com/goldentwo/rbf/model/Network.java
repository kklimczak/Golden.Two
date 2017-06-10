package com.goldentwo.rbf.model;

import com.goldentwo.rbf.model.layers.HiddenLayer;
import com.goldentwo.rbf.model.layers.InputLayer;
import com.goldentwo.rbf.model.layers.OutputLayer;
import com.goldentwo.rbf.model.other.Point;
import com.goldentwo.rbf.utils.FileUtil;
import lombok.Data;

import java.util.List;

@Data
public class Network {
    private InputLayer inputLayer;
    private HiddenLayer hiddenLayer;
    private OutputLayer outputLayer;

    private List<Point> trainingList;

    public Network() {
        this.trainingList = FileUtil.loadPointsFromFile("approximation_train.txt");
    }


}
