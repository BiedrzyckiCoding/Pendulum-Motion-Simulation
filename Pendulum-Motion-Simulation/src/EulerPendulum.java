import java.util.ArrayList;

public class EulerPendulum {

    ArrayList<Double> alpha;
    ArrayList<Double> omega;
    ArrayList<Double> epsilon;
    ArrayList<Double> alphaChange;
    ArrayList<Double> omegaChange;
    ArrayList<Double> xCoord;
    ArrayList<Double> yCoord;
    ArrayList<Double> height;
    ArrayList<Double> timeChange;
    ArrayList<Double> potentialEnergy;
    ArrayList<Double> kineticEnergy;
    ArrayList<Double> totalEnergy;

    double dt = 0.01;//[s]
    double g = -10;//[m/s^2]
    double pendulumLength = 1;//[m]
    double alphaStart = 45;
    double alphaStartRadians = Math.toRadians(alphaStart);

}
