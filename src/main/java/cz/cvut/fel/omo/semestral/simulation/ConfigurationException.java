package cz.cvut.fel.omo.semestral.simulation;

/**
 * Exception class used to indicate a configuration error in the simulation.
 * This exception is thrown when there is an issue with the simulation configuration.
 */
public class ConfigurationException extends Exception{
    /**
     * Constructs a new ConfigurationException with the specified detail message.
     *
     * @param message The detail message describing the configuration error.
     */
    public ConfigurationException(String message) {
        super(message);
    }
}
