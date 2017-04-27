package com.goldentwo.strategies;

import com.goldentwo.models.Node;

public interface Strategy {

    Strategy setPattern(String pattern);
    void run(Node node);

}
