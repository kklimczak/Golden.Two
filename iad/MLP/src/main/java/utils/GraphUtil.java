package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.List;

public class GraphUtil {

    public void printGraph(String title, List<XYDataItem> data, String xLabel, String yLabel){
        final Graph demo = new Graph(title, data, xLabel, yLabel);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

    private class Graph extends ApplicationFrame{
        Graph(String title, List<XYDataItem> dataItems, String xLabel, String yLabel) {
            super(title);
            final XYSeries series = new XYSeries(title);

            for (XYDataItem dataItem : dataItems) {
                series.add(dataItem);
            }

            final XYSeriesCollection data = new XYSeriesCollection(series);
            final JFreeChart chart = ChartFactory.createXYLineChart(
                    title,
                    xLabel,
                    yLabel,
                    data,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(chartPanel);
        }
    }
}
