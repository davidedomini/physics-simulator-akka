package seq;

import lib.Boundary;

import java.util.Calendar;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class SequentialSimulationMain {

    public static void main(String[] args) {

        Boundary bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);

    	SimulationView viewer = new SimulationView(620,620);

        Model m = new Model(5000, bounds);
        m.addObserver(viewer);

    	SimulatorController sim = new SimulatorController(m);
        long s = Calendar.getInstance().getTimeInMillis();
        sim.execute(1000);
        long f = Calendar.getInstance().getTimeInMillis();

        System.out.println("time:" + (f-s));
    }
}
