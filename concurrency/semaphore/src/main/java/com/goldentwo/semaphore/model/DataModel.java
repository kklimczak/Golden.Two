package com.goldentwo.semaphore.model;

import com.goldentwo.semaphore.util.CharGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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

    public void incrementLetterCounter(String letter) {
        Integer counter = this.data.get(letter);
        this.data.put(letter, ++counter);
    }

    public CategoryDataset generateCategoryDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        this.data.forEach(
                (key, count) -> dataSet.addValue(count, key, key)
        );

        return dataSet;
    }
}
