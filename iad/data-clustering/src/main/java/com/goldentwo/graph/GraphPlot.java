package com.goldentwo.graph;

import com.goldentwo.utils.GraphStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.ShapeUtilities;

import javax.swing.*;
import java.awt.*;

public class GraphPlot extends ApplicationFrame {
    public GraphPlot(String s, XYSeriesCollection series, GraphStyle graphStyle) {
        super(s);

        JPanel jpanel = createDemoPanel(series, graphStyle);

        jpanel.setPreferredSize(new Dimension(1000, 600));
        add(jpanel);
    }

    private JPanel createDemoPanel(XYSeriesCollection dataset, GraphStyle graphStyle) {
        JFreeChart jfreechart;

        if (graphStyle.equals(GraphStyle.SCATTER)) {
            jfreechart = ChartFactory.createScatterPlot(
                    "Points and centroids",
                    "X",
                    "Y",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false);


            Shape cross = ShapeUtilities.createDiagonalCross(0.1f, 0.1f);
            Rectangle rectangle = new Rectangle(7, 7);

            XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
            XYItemRenderer renderer = xyPlot.getRenderer();
            renderer.setSeriesShape(0, rectangle);
            renderer.setSeriesPaint(0, Color.red);
            renderer.setSeriesShape(1, cross);
            renderer.setSeriesPaint(1, Color.black);

        } else {
            jfreechart = ChartFactory.createXYLineChart(
                    "Errors",
                    "iteration",
                    "error value",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
            XYItemRenderer renderer = xyPlot.getRenderer();
            renderer.setSeriesPaint(2, Color.BLACK);
        }

        return new ChartPanel(jfreechart);
    }
}
