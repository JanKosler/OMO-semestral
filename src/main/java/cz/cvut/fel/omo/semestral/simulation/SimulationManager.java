package cz.cvut.fel.omo.semestral.simulation;

/**
 * Class for managing the simulation.
 */
public class SimulationManager {
    private HouseFacade houseFacade;

    /**
     * Initializes the simulation.
     */
    public void initializeSimulation() {
        // TODO : check if this needs the simulation config
    }
    /**
     * Starts the simulation.
     */
    public void startSimulation() {
        houseFacade.startSimulation();
    }
}
