package concurrent.NoGui;

import lib.Boundary;
import controller.SimulationController;
import model.SimulationModel;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class SimulationMainNoGUI {

    public static void main(String[] args) {

        long totalIter = 10000;
        int nBodies = 5000;

        SimulationModel simModel = new SimulationModel(nBodies, totalIter);
        int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
        System.out.println("Available CPU: " + (nWorkers-1));
    	SimulationController simController = new SimulationController(simModel, nWorkers);
        simController.execute();
    }
}
