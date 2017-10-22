package com.goldentwo.semaphore.histogram;

import com.goldentwo.semaphore.model.DataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

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

    public void generateHistogram(DataModel dataModel) throws IOException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataModel
                .getData()
                .forEach(
                        (key, count) -> dataset.addValue(count, key, key)
                );

        JFreeChart chart = ChartFactory.createBarChart(PLOT_TITLE, XAXIS, YAXIS,
                dataset, PlotOrientation.VERTICAL, SHOW, TOOL_TIPS, URLS);
        StackedBarRenderer renderer = new StackedBarRenderer(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);
        int width = 1000;
        int height = 600;
        ChartUtilities.saveChartAsPNG(new File(OUTPUT_FILE_NAME), chart, width, height);
    }
}