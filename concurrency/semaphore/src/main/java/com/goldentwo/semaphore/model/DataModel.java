package com.goldentwo.semaphore.model;

import com.goldentwo.semaphore.util.CharGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Integer> counterMap;
    private String[][] data;

    public DataModel(String[][] data) {
        this.data = data;
        this.counterMap = new HashMap<>();
        for (char letter : CharGenerator.CHARS.toCharArray()) {
            counterMap.put(String.valueOf(letter), 0);
        }
    }

    public String[][] getData() {
        return data;
    }

    public void incrementLetterCounter(String letter) {
        Integer counter = this.counterMap.get(letter);
        this.counterMap.put(letter, ++counter);
    }

    public CategoryDataset generateCategoryDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        this.counterMap.forEach(
                (key, count) -> dataSet.addValue(count, key, key)
        );

        return dataSet;
    }
}
