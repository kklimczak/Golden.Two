package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import com.goldentwo.models.MoveType;
import com.goldentwo.models.State;

public class BfsStrategy implements Strategy {

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
        int[] array = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15};
        State state = new State(4, 4, 14, array);

        State newState = State.moveLeft(state);
        if (newState.isSolved()) {
            System.out.println("solved!");
        }
    }

    private void bfs() {

    }
}
