package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import com.goldentwo.models.Summary;
import lombok.NoArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    private String pattern;
    private Set<Node> nodes = new HashSet<>();
    private Node rightNode;

    public Strategy setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Summary run(Node node) {
        prepareToSolve(node);
        long startTime = System.nanoTime();
        dfs(node, 30);
        long endTime = System.nanoTime();

        double spendTimeToSolve = (endTime - startTime) / 1e6;

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        Node solvedPuzzleTemp = rightNode;

        StringBuffer string = new StringBuffer();
        string.append(solvedPuzzleTemp.getMove());
        while (solvedPuzzleTemp.getPreviousNode() != null) {
            if (solvedPuzzleTemp.getPreviousNode().getMove() != null) {
                string.append(solvedPuzzleTemp.getPreviousNode().getMove().toString());
            }
            solvedPuzzleTemp = solvedPuzzleTemp.getPreviousNode();
        }

        return Summary.builder()
                .moves(string.length())
                .path(string.reverse().toString())
                .nodeVisited(nodes.size())
                .spendTime(df.format(spendTimeToSolve))
                .build();
    }

    private void dfs(Node node, int depth) {
        Node next;

        if (depth < 0) {
            return;
        }

        if (node.isSolved()) {
            rightNode = node;
            System.out.println(Arrays.toString(node.getNumbers()));
            System.out.println(depth);
        }

        if (rightNode != null) {
            return;
        }

        for (int i = 0; i < pattern.length(); i++) {
            switch (pattern.charAt(i)) {
                case 'U':
                    next = Node.moveUp(node);

                    if (checkIsCorrectState(depth, next)) return;

                    break;
                case 'D':
                    next = Node.moveDown(node);

                    if (checkIsCorrectState(depth, next)) return;
                    break;
                case 'L':
                    next = Node.moveLeft(node);
                    if (checkIsCorrectState(depth, next)) return;
                    break;
                case 'R':
                    next = Node.moveRight(node);

                    if (checkIsCorrectState(depth, next)) return;
                    break;
            }
        }
    }

    private boolean checkIsCorrectState(int depth, Node next) {
        if (next != null && !nodes.contains(next)) {
            nodes.add(next);

            dfs(next, depth - 1);

            if (rightNode != null) {
                return true;
            }

            nodes.remove(next);

        }
        return false;
    }

    private void prepareToSolve(Node node) {
        nodes.clear();
        nodes.add(node);
    }

}
