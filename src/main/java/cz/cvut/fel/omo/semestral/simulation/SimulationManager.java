package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;

/**
 * Class for managing the simulation.
 */
public class SimulationManager {
    private HouseFacade houseFacade;

    /** Generates a report about the house configuration. */
    private ReportGenerator reportGenerator;
    /**
     * Starts the simulation.
     */
    public void runSimulation(String configFilename) {
        if (houseFacade == null) {
            houseFacade = new HouseFacade();
        }
        houseFacade.runSimulation(configFilename);
    }
}
