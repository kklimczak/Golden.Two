package com.goldentwo;

import com.goldentwo.models.Node;
import com.goldentwo.models.StrategyType;
import com.goldentwo.models.Summary;
import com.goldentwo.strategies.AStarStrategy;
import com.goldentwo.strategies.BfsStrategy;
import com.goldentwo.strategies.DfsStrategy;
import com.goldentwo.strategies.Strategy;
import com.goldentwo.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        StrategyType strategyType = null;
        Strategy strategy = null;

        switch (args[0]) {
            case "bfs":
                strategyType = StrategyType.BFS;
                strategy = new BfsStrategy();
                break;
            case "dfs":
                strategyType = StrategyType.DFS;
                strategy = new DfsStrategy();
                break;
            case "astr":
                strategyType = StrategyType.ASTR;
                strategy = new AStarStrategy();
                break;
            default:
        }

        if (strategyType == null) {
            System.out.println("Wrong strategy");
        } else {
            System.out.println(strategyType.toString());
        }

        Node initialNode = FileUtils.readNodeFromFile(args[2]);

        Summary summary = strategy.setPattern(args[1])
                .run(initialNode);

        String fileName = args[2].split("\\.")[0];

        List<String> solution = new ArrayList<>();
        solution.add(Integer.toString(summary.getMoves()));
        solution.add(summary.getPath());

        FileUtils.writeFile(solution, fileName + "_" + args[0] + "_" + args[1].toLowerCase() + "_sol.txt");

        List<String> stats = new ArrayList<>();
        stats.add(Integer.toString(summary.getMoves()));
        stats.add(Integer.toString(summary.getNodeVisited()));
        stats.add(Integer.toString(summary.getNodeProcessed()));
        stats.add(Integer.toString(summary.getDepth()));
        stats.add(summary.getSpendTime());

        FileUtils.writeFile(stats, fileName + "_" + args[0] + "_" + args[1].toLowerCase() + "_stats.txt");

        System.out.println(summary.toString());
    }
}
