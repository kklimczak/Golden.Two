package com.goldentwo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class State {

    private int sizeX;
    private int sizeY;
    private int blankTilePosition;
    private MoveType move;
    private State previousState;

    private int[] numbers;

    public State(int sizeX, int sizeY, int blankTilePosition, int[] numbers) {
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

    public static State moveUp(State state) {
        if (state.getBlankTilePosition() >= state.getSizeX() * state.getSizeY() - state.getSizeX()) {
            return null;
        }

        int[] newNumbers = new int[state.getNumbers().length];
        System.arraycopy(state.getNumbers(), 0, newNumbers, 0, state.getNumbers().length);

        State next = State.builder()
                .blankTilePosition(state.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(state.getSizeX())
                .sizeY(state.getSizeY())
                .build();

        next.swap(state.getBlankTilePosition(), state.getBlankTilePosition() + state.getSizeX());
        next.setBlankTilePosition(state.getBlankTilePosition() + state.getSizeX());
        next.setMove(MoveType.U);
        next.setPreviousState(state);

        return next;
    }

    public static State moveDown(State state) {
        if (state.getBlankTilePosition() < state.getSizeX()) {
            return null;
        }

        int[] newNumbers = new int[state.getNumbers().length];
        System.arraycopy(state.getNumbers(), 0, newNumbers, 0, state.getNumbers().length);

        State next = State.builder()
                .blankTilePosition(state.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(state.getSizeX())
                .sizeY(state.getSizeY())
                .build();

        next.swap(state.getBlankTilePosition(), state.getBlankTilePosition() - state.getSizeX());
        next.setBlankTilePosition(state.getBlankTilePosition() - state.getSizeX());
        next.setMove(MoveType.D);
        next.setPreviousState(state);

        return next;
    }

    public static State moveRight(State state) {
        if (state.getBlankTilePosition() % state.getSizeY() == 0) {
            return null;
        }

        int[] newNumbers = new int[state.getNumbers().length];
        System.arraycopy(state.getNumbers(), 0, newNumbers, 0, state.getNumbers().length);

        State next = State.builder()
                .blankTilePosition(state.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(state.getSizeX())
                .sizeY(state.getSizeY())
                .build();

        next.swap(state.getBlankTilePosition(), state.getBlankTilePosition() - 1);
        next.setBlankTilePosition(state.getBlankTilePosition() - 1);
        next.setMove(MoveType.R);
        next.setPreviousState(state);

        return next;
    }

    public static State moveLeft(State state) {
        if (state.getBlankTilePosition() % state.getSizeY() == state.getSizeY() - 1) {
            return null;
        }

        int[] newNumbers = new int[state.getNumbers().length];
        System.arraycopy(state.getNumbers(), 0, newNumbers, 0, state.getNumbers().length);

        State next = State.builder()
                .blankTilePosition(state.getBlankTilePosition())
                .numbers(newNumbers)
                .sizeX(state.getSizeX())
                .sizeY(state.getSizeY())
                .build();

        next.swap(state.getBlankTilePosition(), state.getBlankTilePosition() + 1);
        next.setBlankTilePosition(state.getBlankTilePosition() + 1);
        next.setMove(MoveType.L);
        next.setPreviousState(state);

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
