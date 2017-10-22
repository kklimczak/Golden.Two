package com.goldentwo.semaphore.model;

import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Integer> data;

    public DataModel() {
        this.data = new HashMap<>();
        this.data.put("A", 0);
        this.data.put("B", 0);
        this.data.put("C", 0);
        this.data.put("D", 0);
        this.data.put("E", 0);
        this.data.put("F", 0);
        this.data.put("G", 0);
        this.data.put("H", 0);
        this.data.put("I", 0);
        this.data.put("J", 0);
    }

    public Map<String, Integer> getData() {
        return data;
    }
}
