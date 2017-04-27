package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import com.goldentwo.models.State;
import lombok.NoArgsConstructor;

import javax.management.StandardEmitterMBean;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    private String pattern;
    private Board initialBoard;
    private Set<State> states = new HashSet<>();
    private int visited = 0;
    private State rightState;

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
        State state = new State(initialBoard.getSizeX(), initialBoard.getSizeY(), initialBoard.getBlankTilePosition(), initialBoard.getNumbers());
        dfs(state, 50);

    }

    private void dfs(State state, int depth) {
        prepareToSolve(state);

        State next;

        if (depth < 0) {
            return;
        }

        if (state.isSolved()) {
            rightState = state;
            System.out.println(Arrays.toString(state.getNumbers()));
        }

        if (rightState != null) {
            return;
        }

        for (int i = 0; i < pattern.length(); i++) {
            switch (pattern.charAt(i)) {
                case 'U':
                    next = State.moveUp(state);

                    if (checkIsCorrectState(depth, next)) return;

                    break;
                case 'D':
                    next = State.moveDown(state);

                    if (checkIsCorrectState(depth, next)) return;
                    break;
                case 'L':
                    next = State.moveLeft(state);
                    if (checkIsCorrectState(depth, next)) return;
                    break;
                case 'R':
                    next = State.moveRight(state);

                    if (checkIsCorrectState(depth, next)) return;
                    break;
            }
        }
    }

    private boolean checkIsCorrectState(int depth, State next) {
        if (next != null && !states.contains(next)) {
            states.add(next);
            visited++;

            dfs(next, depth-1);

            if (rightState != null) {
                return true;
            }

            states.remove(next);

        }
        return false;
    }

    private void prepareToSolve(State state) {
        states.clear();
        states.add(state);
    }

}
