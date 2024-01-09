package cz.cvut.fel.omo.semestral.manual;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a database of manuals.
 */
public interface ManualDatabase {
    /**
     * Requests a manual from the database.
     * @param id UUID of the manual
     * @return Optional of the manual
     */
    Optional<Manual> requestManual(UUID id);
    /**
     * Requests a manual from the database.
     * @param deviceName name of the device
     * @return Optional of the manual
     */
    Optional<Manual> requestManual(String deviceName);

}
