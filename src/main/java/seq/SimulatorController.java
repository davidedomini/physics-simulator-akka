package seq;

public class SimulatorController {

	private Model model;

	public SimulatorController(Model model) {
		this.model = model;
	}
	
	public void execute(long nSteps) {

		long iter = 0;

		/* simulation loop */

		while (iter < nSteps) {

			/* update bodies */

			model.update(iter);

			/* update iteration */
			iter++;
		}
	}
}
