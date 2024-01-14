package cz.cvut.fel.omo.semestral.simulation;

import cz.cvut.fel.omo.semestral.reporting.ReportGenerator;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for managing the simulation.
 */
@Slf4j
public class SimulationManager {
    private HouseFacade houseFacade;

    /** Generates a report about the house configuration. */
    private ReportGenerator reportGenerator;
    /**
     * Starts the simulation.
     */
    public void runSimulation(String configFilename){
        if (houseFacade == null) {
            houseFacade = new HouseFacade();
        }
        try {
            houseFacade.runSimulation(configFilename);
        } catch (SimulationException e) {
            log.error("Error while running simulation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
