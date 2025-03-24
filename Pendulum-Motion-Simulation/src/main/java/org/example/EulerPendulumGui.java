package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

public class EulerPendulumGui extends JFrame {

    public EulerPendulumGui(List<Double> xCoord, List<Double> yCoord) {
        setTitle("Euler Pendulum Path");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        XYSeries series = new XYSeries("Pendulum Path");

        for (int i = 0; i < xCoord.size(); i++) {
            series.add(xCoord.get(i), yCoord.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Pendulum Trajectory",
                "X Position [m]",
                "Y Position [m]",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }
}
