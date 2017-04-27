package com.goldentwo.strategies;

import com.goldentwo.models.Board;
import com.goldentwo.models.Node;
import lombok.NoArgsConstructor;

import javax.management.StandardEmitterMBean;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    private String pattern;
    private Board initialBoard;
    private Set<Node> nodes = new HashSet<>();
    private int visited = 0;
    private Node rightNode;

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
        Node node = new Node(initialBoard.getSizeX(), initialBoard.getSizeY(), initialBoard.getBlankTilePosition(), initialBoard.getNumbers());
        dfs(node, 50);

    }

    private void dfs(Node node, int depth) {
        prepareToSolve(node);

        Node next;

        if (depth < 0) {
            return;
        }

        if (node.isSolved()) {
            rightNode = node;
            System.out.println(Arrays.toString(node.getNumbers()));
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
            visited++;

            dfs(next, depth-1);

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
