package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    private String pattern;
    private Board initialBoard;

    public Strategy setPattern(String pattern) {
        System.out.println(pattern);
        this.pattern = pattern;
        return this;
    }

    public Strategy setInitialBoard(Board board) {
        this.initialBoard = board;
        System.out.println(board);
        return this;
    }

    public void run() {
        System.out.println("run()");
    }

}
