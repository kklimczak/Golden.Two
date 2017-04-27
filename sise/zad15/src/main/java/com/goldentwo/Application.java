package com.goldentwo;

import com.goldentwo.models.Node;
import com.goldentwo.models.StrategyType;
import com.goldentwo.strategies.AStarStrategy;
import com.goldentwo.strategies.BfsStrategy;
import com.goldentwo.strategies.DfsStrategy;
import com.goldentwo.strategies.Strategy;
import com.goldentwo.utils.FileUtils;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        String paramsLine;
        String[] params;
        StrategyType strategyType = null;
        Strategy strategy = null;

        System.out.println("Podaj parametry:");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            paramsLine = scanner.nextLine();

            params = paramsLine.split(" ");

            switch (params[0]) {
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
                case "exit":
                    System.exit(0);
                default:
            }

            if (strategyType == null) {
                System.out.println("Wrong strategy");
                continue;
            } else {
                System.out.println(strategyType.toString());
            }

            Node initialNode = FileUtils.readNodeFromFile(params[2]);

            strategy.setPattern(params[1])
                    .run(initialNode);

            strategyType = null;
            strategy = null;
        }
    }
}
