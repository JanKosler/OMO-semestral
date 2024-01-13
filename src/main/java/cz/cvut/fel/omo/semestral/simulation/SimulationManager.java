package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;

/**
 * Class for managing the simulation.
 */
public class SimulationManager {
    private HouseFacade houseFacade;

    /** Generates a report about the house configuration. */
    private ReportGenerator reportGenerator;

    private SimulationConfig simulationConfig;
    /**
     * Initializes the simulation.
     */
    public void initializeSimulation(String configFilename) {
        SimulationConfig simulationConfig = new SimulationConfig(configFilename);
    }
    /**
     * Starts the simulation.
     */
    public void startSimulation() {
        houseFacade.startSimulation();
    }
}
