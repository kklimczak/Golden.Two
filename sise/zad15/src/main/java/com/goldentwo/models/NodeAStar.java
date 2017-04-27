package com.goldentwo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeAStar extends Node implements Comparable<NodeAStar> {

    private int hEstimatedMovementCost;
    private int gMovementCost;
    private int heuristic;
    private int[] correctOrder;

    public NodeAStar(int sizeX, int sizeY, int blankTilePosition, int[] numbers, int heuristic) {
        super(sizeX, sizeY, blankTilePosition, numbers);
        this.gMovementCost = 0;
        this.heuristic = heuristic;
        this.correctOrder = new int[sizeX * sizeY];
        for (int i = 0; i < sizeX * sizeY - 1; i++) {
            this.correctOrder[i] = i + 1;
        }

        this.correctOrder[sizeX * sizeY - 1] = 0;
    }

    private int getActualHeuristic() {
        return heuristic == 1 ? manhattanHeuristic() : hammingHeuristic();
    }

    private int getPathScore() {
        return gMovementCost + hEstimatedMovementCost;
    }

    private int manhattanHeuristic() {
        // count tile moves to right position
        int heuristicValue = 0;
        int boardNumbers = sizeX * sizeY;

        for (int i = 0; i < boardNumbers; i++) {
            if (i != getBlankTilePosition()) {
                int rightPosition = numbers[i] - 1;
                int xTile = i / sizeX;
                int yTile = i % sizeY;
                int xRightTile = rightPosition / sizeX;
                int yRightTile = rightPosition % sizeY;
                heuristicValue += Math.abs(xRightTile - xTile);
                heuristicValue += Math.abs(yRightTile - yTile);
            }
        }

        return heuristicValue;
    }

    private int hammingHeuristic() {
        int heuristicValue = 0;
        int boardNumbers = sizeX * sizeY;

        for (int i = 0; i < boardNumbers; i++) {
            if (numbers[i] != correctOrder[i]) {
                heuristicValue++;
            }
        }

        return heuristicValue;
    }

    public static NodeAStar moveUp(NodeAStar state) {
        Node nextBase = Node.moveUp(state);

        return countCostAndEstimatedCost(state, nextBase);
    }

    public static NodeAStar moveDown(NodeAStar state) {
        Node nextBase = Node.moveDown(state);

        return countCostAndEstimatedCost(state, nextBase);
    }

    public static NodeAStar moveLeft(NodeAStar state) {
        Node nextBase = Node.moveLeft(state);

        return countCostAndEstimatedCost(state, nextBase);
    }

    public static NodeAStar moveRight(NodeAStar state) {
        Node nextBase = Node.moveRight(state);

        return countCostAndEstimatedCost(state, nextBase);
    }

    private static NodeAStar countCostAndEstimatedCost(NodeAStar state, Node nextBase) {
        NodeAStar next = null;

        if (nextBase != null) {
            next = new NodeAStar(nextBase.getSizeX(), nextBase.getSizeY(), nextBase.getBlankTilePosition(), nextBase.getNumbers(), state.getHeuristic());
            next.setMove(nextBase.getMove());
            next.setPreviousNode(state);

            next.gMovementCost = state.gMovementCost + 1;
            next.hEstimatedMovementCost = next.getActualHeuristic();
        }

        return next;
    }

    public int compareTo(NodeAStar state) {
        int actualPathScore = getPathScore();
        int comparedPathScore = state.getPathScore();

        if (actualPathScore > comparedPathScore) {
            return 1;
        } else if (actualPathScore < comparedPathScore) {
            return -1;
        } else {
            return 0;
        }
    }


}
