package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import com.goldentwo.models.Summary;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class BfsStrategy implements Strategy {

    private String pattern;
    private Set<Node> nodes = new HashSet<>();
    private Queue<Node> nodeQueue = new LinkedList<>();
    private Summary summary;

    private Node solvedPuzzle;

    public Strategy setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Summary run(Node node) {
        long startTime = System.nanoTime();
        bfs(node);
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

        summary = Summary.builder()
                .moves(string.length())
                .path(string.reverse().toString())
                .nodeVisited(nodes.size())
                .nodeProcessed(nodeQueue.size())
                .depth(string.length())
                .spendTime(df.format(spendTimeToSolve))
                .build();

        return summary;
    }

    private void bfs(Node node) {
        prepareToSolve(node);

        Node next;

        while (!nodeQueue.isEmpty()) {
            node = nodeQueue.poll();

            if (node.isSolved()) {
                solvedPuzzle = node;
                break;
            }

            for (int i = 0; i < pattern.length(); i++) {
                switch (pattern.charAt(i)) {
                    case 'U':

                        next = Node.moveUp(node);
                        checkIsNodeCorrect(next);

                        break;
                    case 'D':

                        next = Node.moveDown(node);
                        checkIsNodeCorrect(next);

                        break;
                    case 'L':
                        next = Node.moveLeft(node);
                        checkIsNodeCorrect(next);

                        break;
                    case 'R':
                        next = Node.moveRight(node);
                        checkIsNodeCorrect(next);

                        break;
                }

            }

        }
    }

    private void checkIsNodeCorrect(Node next) {
        if (next != null && !nodes.contains(next)) {

            nodes.add(next);
            nodeQueue.add(next);

        }
    }

    private void prepareToSolve(Node node) {
        nodes.clear();
        nodeQueue.clear();

        nodes.add(node);
        nodeQueue.add(node);

    }
}
