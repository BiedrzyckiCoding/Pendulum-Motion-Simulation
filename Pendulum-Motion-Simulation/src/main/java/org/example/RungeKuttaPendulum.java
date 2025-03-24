package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class RungeKuttaPendulum {

    ArrayList<Double> alpha = new ArrayList<>();
    ArrayList<Double> omega = new ArrayList<>();
    ArrayList<Double> epsilon = new ArrayList<>();
    ArrayList<Double> xCoord = new ArrayList<>();
    ArrayList<Double> yCoord = new ArrayList<>();
    ArrayList<Double> height = new ArrayList<>();
    ArrayList<Double> timeChange = new ArrayList<>();
    ArrayList<Double> potentialEnergy = new ArrayList<>();
    ArrayList<Double> kineticEnergy = new ArrayList<>();
    ArrayList<Double> totalEnergy = new ArrayList<>();

    double dt = 0.01; //[s]
    double g = -10; //[m/s^2] (negative downward)
    double pendulumLength = 1; //[m]
    double alphaStart = 45; //[deg]
    double alphaStartRadians = Math.toRadians(alphaStart);
    double mass = 1; //[kg]

    public void simulate(int steps) {
        //initial conditions
        double a = alphaStartRadians;  //alpha
        double o = 0; //omega

        for (int i = 0; i < steps; i++) {

            //current time
            double t = i * dt;

            //store current alpha, omega
            alpha.add(a);
            omega.add(o);

            //angular acceleration (for reference)
            double eps = (g / pendulumLength) * Math.sin(a);
            epsilon.add(eps);

            //compute energies from current state
            //position (a), velocity (o)
            //(x, y)
            double x = pendulumLength * Math.cos(a - Math.PI / 2);
            double y = pendulumLength * Math.sin(a - Math.PI / 2);
            xCoord.add(x);
            yCoord.add(y);

            //height above the pendulum's lowest point
            double h = pendulumLength + y;
            height.add(h);

            //potential energy, Kinetic energy, Total
            double ep = mass * Math.abs(g) * h;
            double ek = 0.5 * mass * Math.pow(pendulumLength * o, 2);
            double et = ep + ek;
            potentialEnergy.add(ep);
            kineticEnergy.add(ek);
            totalEnergy.add(et);

            //time
            timeChange.add(t);


            //Runge–Kutta 4th order calculations:

            //k1
            double k1Alpha = fAlpha(a, o);
            double k1Omega = fOmega(a);

            //k2
            double k2Alpha = fAlpha(a + 0.5 * dt * k1Alpha, o + 0.5 * dt * k1Omega);
            double k2Omega = fOmega(a + 0.5 * dt * k1Alpha);

            //k3
            double k3Alpha = fAlpha(a + 0.5 * dt * k2Alpha, o + 0.5 * dt * k2Omega);
            double k3Omega = fOmega(a + 0.5 * dt * k2Alpha);

            //k4
            double k4Alpha = fAlpha(a + dt * k3Alpha, o + dt * k3Omega);
            double k4Omega = fOmega(a + dt * k3Alpha);

            //update alpha, omega
            a += (dt / 6.0) * (k1Alpha + 2.0 * k2Alpha + 2.0 * k3Alpha + k4Alpha);
            o += (dt / 6.0) * (k1Omega + 2.0 * k2Omega + 2.0 * k3Omega + k4Omega);
        }
    }

    //fAlpha( alpha, omega ) = omega
    private double fAlpha(double alpha, double omega) {
        return omega;
    }

    //fOmega( alpha ) = (g/l)*sin(alpha)
    private double fOmega(double alpha) {
        return (g / pendulumLength) * Math.sin(alpha);
    }
    public static void main(String[] args) {
        RungeKuttaPendulum sim = new RungeKuttaPendulum();
        sim.simulate(200); //2 seconds
        sim.simulate(10000); //should be constant now, or look "normal"

        //total energy should be constant now
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

        for (int i = 0; i < sim.alpha.size(); i++) {
            System.out.printf(
                    "t=%.2f, alpha=%.5f, omega=%.5f, Eps=%.5f, x=%.5f, y=%.5f, Ep=%.5f, Ek=%.5f, Et=%.5f%n",
                    sim.timeChange.get(i),
                    sim.alpha.get(i),
                    sim.omega.get(i),
                    sim.epsilon.get(i),
                    sim.xCoord.get(i),
                    sim.yCoord.get(i),
                    sim.potentialEnergy.get(i),
                    sim.kineticEnergy.get(i),
                    sim.totalEnergy.get(i)
            );
        }
    }
}
