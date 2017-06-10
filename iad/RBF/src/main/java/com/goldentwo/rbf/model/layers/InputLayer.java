package com.goldentwo.rbf.model.layers;

import com.goldentwo.rbf.model.other.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputLayer {
    private List<Point> points = new ArrayList<>();
}
