package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import com.goldentwo.models.Summary;

public interface Strategy {

    Strategy setPattern(String pattern);
    Summary run(Node node);

}
