package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import com.goldentwo.models.NodeAStar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarStrategy implements Strategy {

    private String pattern;
    private Board initialBoard;
    private PriorityQueue<NodeAStar> openNodes = new PriorityQueue<>();
    private Set<NodeAStar> closeNodes = new HashSet<>();
    private int visited;

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

        NodeAStar stateAStar = new NodeAStar(initialBoard.getSizeX(), initialBoard.getSizeY(), initialBoard.getBlankTilePosition(),
                initialBoard.getNumbers(), 0);

        astr(stateAStar);
    }

    private void astr(NodeAStar state) {

        prepareToSolve(state);

        NodeAStar next;

        while(!openNodes.isEmpty()) {
            state = openNodes.poll();

            if (state.isSolved()) {
                System.out.println(Arrays.toString(state.getNumbers()));
                break;
            }

            if (closeNodes.contains(state)) {
                continue;
            }

            closeNodes.add(state);

            next = NodeAStar.moveUp(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
                visited++;
            }

            next = NodeAStar.moveDown(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
                visited++;
            }

            next = NodeAStar.moveLeft(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
                visited++;
            }

            next = NodeAStar.moveRight(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
                visited++;
            }

        }

    }

    private void prepareToSolve(NodeAStar state) {
        openNodes.clear();
        closeNodes.clear();
        openNodes.add(state);
    }


}
