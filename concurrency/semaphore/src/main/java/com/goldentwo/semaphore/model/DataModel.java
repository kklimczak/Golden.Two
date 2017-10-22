package com.goldentwo.semaphore.model;

import com.goldentwo.semaphore.util.CharGenerator;

import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Integer> data;

    public DataModel() {
        this.data = new HashMap<>();
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            data.put(String.valueOf(letter), 0);
        }
    }

    public Map<String, Integer> getData() {
        return data;
    }
}
