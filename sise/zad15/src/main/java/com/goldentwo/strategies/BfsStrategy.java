package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import com.goldentwo.models.MoveType;
import com.goldentwo.models.State;

import java.util.*;

public class BfsStrategy implements Strategy {

    private String pattern;
    private Board initialBoard;
    private Set<State> states = new HashSet<>();
    private Queue<State> stateQueue = new LinkedList<>();
    private int visited = 0;

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

        bfs(state);
        System.out.println("run() end");
    }

    private void bfs(State state) {
        prepareToSolve(state);

        State next;

        while (!stateQueue.isEmpty()) {
            visited++;
            state = stateQueue.poll();

            if (state.isSolved()) {
                System.out.println("");
                System.out.println(Arrays.toString(state.getNumbers()));
                break;
            }

            for (int i = 0; i < pattern.length(); i++) {
                switch (pattern.charAt(i)) {
                    case 'U':

                        next = State.moveUp(state);
                        checkIsStateCorrect(next);

                        break;
                    case 'D':

                        next = State.moveDown(state);
                        checkIsStateCorrect(next);

                        break;
                    case 'L':
                        next = State.moveLeft(state);
                        checkIsStateCorrect(next);

                        break;
                    case 'R':
                        next = State.moveRight(state);
                        checkIsStateCorrect(next);

                        break;
                }

            }

        }
    }

    private void checkIsStateCorrect(State next) {
        if (next != null && !states.contains(next)) {

            states.add(next);
            stateQueue.add(next);
            visited++;

        }
    }

    private void prepareToSolve(State state) {
        states.clear();
        stateQueue.clear();

        states.add(state);
        stateQueue.add(state);

    }
}
