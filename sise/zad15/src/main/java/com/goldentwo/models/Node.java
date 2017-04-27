package com.goldentwo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    protected int sizeX;
    protected int sizeY;
    protected int blankTilePosition;
    protected MoveType move;
    protected Node previousNode;

    protected int[] numbers;

    public Node(int sizeX, int sizeY, int blankTilePosition, int[] numbers) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blankTilePosition = blankTilePosition;
        this.numbers = numbers;
    }

    public boolean isSolved() {
        int boardNumbers = sizeX * sizeY;

        for (int i = 1; i < boardNumbers; i++) {
            if (i != numbers[i-1]) {
                return false;
            }
        }
        return true;
    }

    private void swap(int tilePosition1, int tilePosition2) {
        int temp;

        temp = numbers[tilePosition1];
        numbers[tilePosition1] = numbers[tilePosition2];
        numbers[tilePosition2] = temp;

    }

    public static Node moveUp(Node node) {
        if (node.getBlankTilePosition() >= node.getSizeX() * node.getSizeY() - node.getSizeX()) {
            return null;
        }

        int[] newNumbers = new int[node.getNumbers().length];
        System.arraycopy(node.getNumbers(), 0, newNumbers, 0, node.getNumbers().length);

        Node next = Node.builder()
                .blankTilePosition(node.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(node.getSizeX())
                .sizeY(node.getSizeY())
                .build();

        next.swap(node.getBlankTilePosition(), node.getBlankTilePosition() + node.getSizeX());
        next.setBlankTilePosition(node.getBlankTilePosition() + node.getSizeX());
        next.setMove(MoveType.U);
        next.setPreviousNode(node);

        return next;
    }

    public static Node moveDown(Node node) {
        if (node.getBlankTilePosition() < node.getSizeX()) {
            return null;
        }

        int[] newNumbers = new int[node.getNumbers().length];
        System.arraycopy(node.getNumbers(), 0, newNumbers, 0, node.getNumbers().length);

        Node next = Node.builder()
                .blankTilePosition(node.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(node.getSizeX())
                .sizeY(node.getSizeY())
                .build();

        next.swap(node.getBlankTilePosition(), node.getBlankTilePosition() - node.getSizeX());
        next.setBlankTilePosition(node.getBlankTilePosition() - node.getSizeX());
        next.setMove(MoveType.D);
        next.setPreviousNode(node);

        return next;
    }

    public static Node moveRight(Node node) {
        if (node.getBlankTilePosition() % node.getSizeY() == 0) {
            return null;
        }

        int[] newNumbers = new int[node.getNumbers().length];
        System.arraycopy(node.getNumbers(), 0, newNumbers, 0, node.getNumbers().length);

        Node next = Node.builder()
                .blankTilePosition(node.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(node.getSizeX())
                .sizeY(node.getSizeY())
                .build();

        next.swap(node.getBlankTilePosition(), node.getBlankTilePosition() - 1);
        next.setBlankTilePosition(node.getBlankTilePosition() - 1);
        next.setMove(MoveType.R);
        next.setPreviousNode(node);

        return next;
    }

    public static Node moveLeft(Node node) {
        if (node.getBlankTilePosition() % node.getSizeY() == node.getSizeY() - 1) {
            return null;
        }

        int[] newNumbers = new int[node.getNumbers().length];
        System.arraycopy(node.getNumbers(), 0, newNumbers, 0, node.getNumbers().length);

        Node next = Node.builder()
                .blankTilePosition(node.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(node.getSizeX())
                .sizeY(node.getSizeY())
                .build();

        next.swap(node.getBlankTilePosition(), node.getBlankTilePosition() + 1);
        next.setBlankTilePosition(node.getBlankTilePosition() + 1);
        next.setMove(MoveType.L);
        next.setPreviousNode(node);

        return next;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int numbersSize = sizeX * sizeY - 1;
        for (int i = 0; i < numbersSize; i++) {
            hash *= 31;
            hash += numbers[i];
        }
        return hash;

    }

}
