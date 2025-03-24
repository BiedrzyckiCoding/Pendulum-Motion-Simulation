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
        // Initial values
        double a = alphaStartRadians; // alpha
        double o = 0;                 // omega

        for (int i = 0; i < steps; i++) {
            //time for the current row
            double t = i * dt;

            //compute angular acceleration from the *old* alpha
            double eps = (g / pendulumLength) * Math.sin(a);

            //store current alpha, omega, epsilon
            alpha.add(a);
            omega.add(o);
            epsilon.add(eps);

            //compute the small changes
            double da = o * dt;//delta alpha
            double doVal = eps * dt;//delta omega
            alphaChange.add(da);
            omegaChange.add(doVal);

            //compute (x, y) from the *old* alpha
            double x = pendulumLength * Math.cos(a - Math.PI / 2);
            double y = pendulumLength * Math.sin(a - Math.PI / 2);
            xCoord.add(x);
            yCoord.add(y);

            //height above lowest point (y is negative downward)
            double h = pendulumLength + y;
            height.add(h);

            //store time
            timeChange.add(t);

            //energies from the *old* omega, old height
            double ep = mass * Math.abs(g) * h;                 // Potential
            double ek = 0.5 * mass * Math.pow(pendulumLength * o, 2); // Kinetic
            double et = ep + ek;

            potentialEnergy.add(ep);
            kineticEnergy.add(ek);
            totalEnergy.add(et);

            //finally, update alpha & omega for the *next* step
            o += doVal;
            a += da;
        }
    }

    public static void main(String[] args) {
        EulerPendulum sim = new EulerPendulum();
        sim.simulate(105); //simulate 105 steps to see the different position and to have the same as the excel has lol

        SwingUtilities.invokeLater(() -> {
            EulerPendulumGui gui = new EulerPendulumGui(
                    sim.xCoord,
                    sim.yCoord,
                    sim.timeChange,
                    sim.potentialEnergy,
                    sim.kineticEnergy,
                    sim.totalEnergy
            );
            gui.setVisible(true);
        });

        //example output
        for (int i = 0; i < sim.alpha.size(); i++) {
            System.out.printf(
                    "Alpha = %.6f, Omega = %.6f, Eps = %.8f, Dalpha = %.8f, Domega = %.8f, x = %.8f, y = %.8f, H = %.6f, t = %.2f, Ep = %.8f, Ek = %.8f, Et = %.6f%n",
                    sim.alpha.get(i),
                    sim.omega.get(i),
                    sim.epsilon.get(i),
                    sim.alphaChange.get(i),
                    sim.omegaChange.get(i),
                    sim.xCoord.get(i),
                    sim.yCoord.get(i),
                    sim.height.get(i),
                    sim.timeChange.get(i),
                    sim.potentialEnergy.get(i),
                    sim.kineticEnergy.get(i),
                    sim.totalEnergy.get(i)
            );
        }
    }
}
