package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import com.goldentwo.models.NodeAStar;
import com.goldentwo.models.Summary;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class AStarStrategy implements Strategy {

    private String pattern;
    private PriorityQueue<NodeAStar> openNodes = new PriorityQueue<>();
    private Set<NodeAStar> closeNodes = new HashSet<>();

    private Node solvedPuzzle;

    public Strategy setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Summary run(Node node) {

        NodeAStar stateAStar = new NodeAStar(
                node.getSizeX(),
                node.getSizeY(),
                node.getBlankTilePosition(),
                node.getNumbers(),
                getHeuristicFromPattern()
        );

        long startTime = System.nanoTime();
        astr(stateAStar);
        long endTime = System.nanoTime();

        double spendTimeToSolve = (endTime - startTime) / 1e6;

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        Node solvedPuzzleTemp = solvedPuzzle;

        StringBuffer string = new StringBuffer();
        string.append(solvedPuzzleTemp.getMove());
        while (solvedPuzzleTemp.getPreviousNode() != null) {
            if (solvedPuzzleTemp.getPreviousNode().getMove() != null) {
                string.append(solvedPuzzleTemp.getPreviousNode().getMove().toString());
            }
            solvedPuzzleTemp = solvedPuzzleTemp.getPreviousNode();
        }

        System.out.println(Arrays.toString(solvedPuzzle.getNumbers()));

        return Summary.builder()
                .moves(string.length())
                .path(string.reverse().toString())
                .nodeVisited(openNodes.size())
                .nodeProcessed(closeNodes.size())
                .spendTime(df.format(spendTimeToSolve))
                .build();
    }

    private int getHeuristicFromPattern() {
        if (pattern.equals("manh")) {
            return 1;
        } else if (pattern.equals("hamm")) {
            return 0;
        }
        return -1;
    }

    private void astr(NodeAStar state) {

        prepareToSolve(state);

        NodeAStar next;

        while (!openNodes.isEmpty()) {
            state = openNodes.poll();

//            state = openNodes.stream()
//                    .min(Comparator.comparingInt(NodeAStar::getHEstimatedMovementCost))
//                    .orElse(null);

            openNodes.remove(state);

            System.out.println(Arrays.toString(state.getNumbers()));
//            System.out.println(state.getMove());

            if (state.isSolved()) {
                System.out.println(Arrays.toString(state.getNumbers()));
                solvedPuzzle = state;
                break;
            }

            if (!closeNodes.contains(state)) {
                closeNodes.add(state);
            }



            next = NodeAStar.moveUp(state);

            if (next != null) {
                openNodes.add(next);
            }

            next = NodeAStar.moveDown(state);

            if (next != null) {
                openNodes.add(next);
            }

            next = NodeAStar.moveLeft(state);

            if (next != null) {
                openNodes.add(next);
            }

            next = NodeAStar.moveRight(state);

            if (next != null) {
                openNodes.add(next);
            }

        }

    }

    private void prepareToSolve(NodeAStar state) {
        openNodes.clear();
        closeNodes.clear();
        openNodes.add(state);
    }


}
