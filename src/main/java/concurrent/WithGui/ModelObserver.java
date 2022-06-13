package concurrent.WithGui;

import concurrent.SimulationModel;

public interface ModelObserver {

    void modelUpdated(SimulationModel model);
}
