package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import com.goldentwo.models.NodeAStar;
import com.goldentwo.models.Summary;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AStarStrategy implements Strategy {

    private String pattern;
    private PriorityQueue<NodeAStar> openNodes = new PriorityQueue<>();
    private Set<NodeAStar> closeNodes = new HashSet<>();
    private Summary summary;

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

        summary = Summary.builder()
                .moves(string.length())
                .path(string.reverse().toString())
                .nodeVisited(openNodes.size())
                .nodeProcessed(closeNodes.size())
                .depth(string.length())
                .spendTime(df.format(spendTimeToSolve))
                .build();

        return summary;
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

        LocalDateTime astrStartTime = LocalDateTime.now();
        while (!openNodes.isEmpty()) {
            LocalDateTime checkTime = LocalDateTime.now();

            if (ChronoUnit.MILLIS.between(astrStartTime, checkTime) >= 500) {
                System.exit(1);
            }

            state = openNodes.poll();

            if (state.isSolved()) {
                System.out.println(Arrays.toString(state.getNumbers()));
                solvedPuzzle = state;
                break;
            }

            if (closeNodes.contains(state)) {
                continue;
            }

            closeNodes.add(state);

            next = NodeAStar.moveUp(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
            }

            next = NodeAStar.moveDown(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
            }

            next = NodeAStar.moveRight(state);

            if (next != null && !closeNodes.contains(next)) {
                openNodes.add(next);
            }

            next = NodeAStar.moveLeft(state);

            if (next != null && !closeNodes.contains(next)) {
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
