package com.goldentwo.rbf.graph;

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
    public GraphPlot(String s, XYSeriesCollection series) {
        super(s);

        JPanel jpanel = createDemoPanel(series);

        jpanel.setPreferredSize(new Dimension(1000, 600));
        add(jpanel);
    }

    private JPanel createDemoPanel(XYSeriesCollection dataset) {
        JFreeChart jfreechart;


        jfreechart = ChartFactory.createScatterPlot(
                "Approximation result",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);


        Shape cross = ShapeUtilities.createDiagonalCross(1f, 1f);
        Rectangle rectangle = new Rectangle(7, 7);

        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, rectangle);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShape(1, cross);
        renderer.setSeriesPaint(1, Color.darkGray);
        renderer.setSeriesShape(2, cross);
        renderer.setSeriesPaint(2, Color.BLACK);


        return new ChartPanel(jfreechart);
    }
}
