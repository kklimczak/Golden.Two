package com.goldentwo.strategies;

import com.goldentwo.models.Node;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class DfsStrategy implements Strategy {

    private String pattern;
    private Set<Node> nodes = new HashSet<>();
    private int visited = 0;
    private Node rightNode;

    public Strategy setPattern(String pattern) {
        System.out.println(pattern);
        this.pattern = pattern;
        return this;
    }

    public void run(Node node) {
        System.out.println("run()");
        dfs(node, 50);
        System.out.println("run() end");
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
