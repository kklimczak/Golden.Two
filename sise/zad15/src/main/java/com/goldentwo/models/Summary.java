package com.goldentwo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Summary {

    private int moves;
    private String path;
    private int nodeVisited;
    private int nodeProcessed;
    private int depth;
    private String spendTime;

}
