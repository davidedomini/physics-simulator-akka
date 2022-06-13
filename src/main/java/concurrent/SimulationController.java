package concurrent;

import concurrent.WithGui.StopFlag;

public class SimulationController {

	private SimulationModel simModel;
	private int nWorkers;
	private Master master;
	private StopFlag stopFlag;

	public SimulationController(SimulationModel simModel, int nWorkers) {
		this.simModel = simModel;
		this.nWorkers = nWorkers;
		this.stopFlag = new StopFlag(false);
	}

	public void stop(){
		if(master != null){
			System.out.println("Stop invoked");
			stopFlag.setStopFlag(true);
		}
	}

	public void restart(){
		simModel.reset();
		stopFlag.setStopFlag(false);
		master = new Master(simModel, nWorkers, stopFlag);
		master.start();
	}
	
	public void execute() {
		master = new Master(simModel, nWorkers, stopFlag);
		master.start();
	}
}
