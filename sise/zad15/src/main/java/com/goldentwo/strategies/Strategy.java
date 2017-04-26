package com.goldentwo.strategies;

import com.goldentwo.models.Board;

public interface Strategy {

    Strategy setPattern(String pattern);
    Strategy setInitialBoard(Board board);
    void run();

}
