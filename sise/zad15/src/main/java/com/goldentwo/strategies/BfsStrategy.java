package com.goldentwo.strategies;

import com.goldentwo.models.Node;

import java.util.*;

public class BfsStrategy implements Strategy {

    private String pattern;
    private Set<Node> nodes = new HashSet<>();
    private Queue<Node> nodeQueue = new LinkedList<>();
    private int visited = 0;

    public Strategy setPattern(String pattern) {
        System.out.println(pattern);
        this.pattern = pattern;
        return this;
    }

    public void run(Node node) {
        System.out.println("run()");
        bfs(node);
        System.out.println("run() end");
    }

    private void bfs(Node node) {
        prepareToSolve(node);

        Node next;

        while (!nodeQueue.isEmpty()) {
            visited++;
            node = nodeQueue.poll();

            if (node.isSolved()) {
                System.out.println(Arrays.toString(node.getNumbers()));
                break;
            }

            for (int i = 0; i < pattern.length(); i++) {
                switch (pattern.charAt(i)) {
                    case 'U':

                        next = Node.moveUp(node);
                        checkIsStateCorrect(next);

                        break;
                    case 'D':

                        next = Node.moveDown(node);
                        checkIsStateCorrect(next);

                        break;
                    case 'L':
                        next = Node.moveLeft(node);
                        checkIsStateCorrect(next);

                        break;
                    case 'R':
                        next = Node.moveRight(node);
                        checkIsStateCorrect(next);

                        break;
                }

            }

        }
    }

    private void checkIsStateCorrect(Node next) {
        if (next != null && !nodes.contains(next)) {

            nodes.add(next);
            nodeQueue.add(next);
            visited++;

        }
    }

    private void prepareToSolve(Node node) {
        nodes.clear();
        nodeQueue.clear();

        nodes.add(node);
        nodeQueue.add(node);

    }
}
