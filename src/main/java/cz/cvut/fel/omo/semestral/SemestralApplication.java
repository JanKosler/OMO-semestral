package cz.cvut.fel.omo.semestral;

import cz.cvut.fel.omo.semestral.simulation.SimulationConfig;
import cz.cvut.fel.omo.semestral.simulation.SimulationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Semestral Application. This class is responsible for starting the application
 * and running the simulation using a specified configuration file.
 */
@SpringBootApplication
public class SemestralApplication {

	/**
	 * The entry point of the application.
	 *
	 * @param args The command line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		SimulationManager simulationManager = new SimulationManager();
		simulationManager.runSimulation("testConfig1.json");
	}

}
