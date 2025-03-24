package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class EulerPendulum {

    ArrayList<Double> alpha = new ArrayList<>();
    ArrayList<Double> omega = new ArrayList<>();
    ArrayList<Double> epsilon = new ArrayList<>();
    ArrayList<Double> alphaChange = new ArrayList<>();
    ArrayList<Double> omegaChange = new ArrayList<>();
    ArrayList<Double> xCoord = new ArrayList<>();
    ArrayList<Double> yCoord = new ArrayList<>();
    ArrayList<Double> height = new ArrayList<>();
    ArrayList<Double> timeChange = new ArrayList<>();
    ArrayList<Double> potentialEnergy = new ArrayList<>();
    ArrayList<Double> kineticEnergy = new ArrayList<>();
    ArrayList<Double> totalEnergy = new ArrayList<>();

    double dt = 0.01;//[s]
    double g = -10;//[m/s^2]
    double pendulumLength = 1;//[m]
    double alphaStart = 45;
    double alphaStartRadians = Math.toRadians(alphaStart);
    double mass = 1; //[kg]

    public void simulate(int steps) {
        //initial values
        double a = alphaStartRadians;
        double o = 0;

        for (int i = 0; i < steps; i++) {
            //calculate angular acceleration (epsilon)
            double eps = (g / pendulumLength) * Math.sin(a);

            //store current state
            alpha.add(a);
            omega.add(o);
            epsilon.add(eps);

            double da = o * dt;
            double doVal = eps * dt;

            alphaChange.add(da);
            omegaChange.add(doVal);

            //update values for next iteration
            o += doVal;
            a += da;

            //coordinates
            double x = pendulumLength * Math.cos(a - Math.PI / 2);
            double y = pendulumLength * Math.sin(a - Math.PI / 2);
            xCoord.add(x);
            yCoord.add(y);

            //height above lowest point
            double h = pendulumLength + y; // y is negative downwards
            height.add(h);

            //time
            double t = i * dt;
            timeChange.add(t);

            //energies
            double ep = mass * Math.abs(g) * h;
            double ek = mass * Math.pow(pendulumLength * o, 2) / 2.0;
            double et = ep + ek;

            potentialEnergy.add(ep);
            kineticEnergy.add(ek);
            totalEnergy.add(et);
        }
    }

    public static void main(String[] args) {
        EulerPendulum sim = new EulerPendulum();
        sim.simulate(200); //simulate 200 steps (here: 2 seconds)

        //example output
        for (int i = 0; i < sim.alpha.size(); i++) {
            System.out.println("t = " + sim.timeChange.get(i) + ", x = " + sim.xCoord.get(i) + ", y = " + sim.yCoord.get(i));
        }

        SwingUtilities.invokeLater(() -> {
            EulerPendulumGui gui = new EulerPendulumGui(sim.xCoord, sim.yCoord);
            gui.setVisible(true);
        });
    }
}
