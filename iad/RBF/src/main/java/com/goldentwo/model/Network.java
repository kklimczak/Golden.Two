package com.goldentwo.model;

import com.goldentwo.model.layers.HiddenLayer;
import com.goldentwo.model.layers.InputLayer;
import com.goldentwo.model.layers.OutputLayer;
import com.goldentwo.model.other.Point;
import com.goldentwo.utils.FileUtil;
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
