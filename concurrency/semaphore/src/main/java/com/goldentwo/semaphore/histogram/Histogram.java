package com.goldentwo.semaphore.histogram;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;

import java.io.File;
import java.io.IOException;

public class Histogram {
    private static final boolean TOOL_TIPS = false;
    private static final boolean SHOW = false;
    private static final boolean URLS = false;
    private static final String PLOT_TITLE = "Histogram";
    private static final String XAXIS = "letters";
    private static final String YAXIS = "value";
    private static final String OUTPUT_FILE_NAME = "histogram.PNG";
    private static final int OUTPUT_FILE_WIDTH = 1000;
    private static final int OUTPUT_FILE_HEIGHT = 600;

    public void generateHistogram(CategoryDataset dataSet) throws IOException {

        JFreeChart chart = ChartFactory.createBarChart(PLOT_TITLE, XAXIS, YAXIS,
                dataSet, PlotOrientation.VERTICAL, SHOW, TOOL_TIPS, URLS);
        StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);
        ChartUtilities.saveChartAsPNG(new File(OUTPUT_FILE_NAME), chart, OUTPUT_FILE_WIDTH, OUTPUT_FILE_HEIGHT);
    }
}