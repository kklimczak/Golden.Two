package com.goldentwo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Board {

    private int sizeX;
    private int sizeY;
    private int[] numbers;
    private int blankTilePosition;

    @Override
    public String toString() {
        return "Board{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", numbers=" + Arrays.toString(numbers) +
                ", blankTilePosition=" + blankTilePosition +
                '}';
    }
}
