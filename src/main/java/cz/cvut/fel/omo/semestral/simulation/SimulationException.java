package cz.cvut.fel.omo.semestral.simulation;

/**
 * This class represents an exception specific to the simulation in the application.
 * It is thrown when there is an issue related to the simulation process.
 */
public class SimulationException extends Exception {
    /**
     * Constructs a new SimulationException with the specified detail message.
     *
     * @param message The detail message that describes the reason for the exception.
     */
    public SimulationException(String message) {
        super(message);
    }
}
