package cz.cvut.fel.omo.semestral;

import cz.cvut.fel.omo.semestral.simulation.SimulationConfig;
import cz.cvut.fel.omo.semestral.simulation.SimulationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SemestralApplication {

	public static void main(String[] args) {
		SimulationManager simulationManager = new SimulationManager();
		simulationManager.runSimulation("testConfig2.json");
	}

}
