package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EulerPendulumGui extends JFrame {

    public EulerPendulumGui(List<Double> xCoord, List<Double> yCoord,
                            List<Double> time, List<Double> potential,
                            List<Double> kinetic, List<Double> total) {
        setTitle("Euler Pendulum Simulation");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        //first Chart: Pendulum Trajectory
        XYSeries trajectory = new XYSeries("Pendulum Path");
        for (int i = 0; i < xCoord.size(); i++) {
            trajectory.add(xCoord.get(i), yCoord.get(i));
        }

        XYSeriesCollection dataset1 = new XYSeriesCollection(trajectory);
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "Pendulum Trajectory",
                "X Position [m]",
                "Y Position [m]",
                dataset1,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel1 = new ChartPanel(chart1);

        //second Chart: Energies
        XYSeries epSeries = new XYSeries("Potential Energy");
        XYSeries ekSeries = new XYSeries("Kinetic Energy");
        XYSeries etSeries = new XYSeries("Total Energy");

        for (int i = 0; i < time.size(); i++) {
            double t = time.get(i);
            epSeries.add(t, potential.get(i));
            ekSeries.add(t, kinetic.get(i));
            etSeries.add(t, total.get(i));
        }

        XYSeriesCollection dataset2 = new XYSeriesCollection();
        dataset2.addSeries(epSeries);
        dataset2.addSeries(ekSeries);
        dataset2.addSeries(etSeries);

        JFreeChart chart2 = ChartFactory.createXYLineChart(
                "Energy vs Time",
                "Time [s]",
                "Energy [J]",
                dataset2,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        ChartPanel chartPanel2 = new ChartPanel(chart2);

        //add both panels to the frame
        add(chartPanel1);
        add(chartPanel2);
    }
}
