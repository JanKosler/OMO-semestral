package cz.cvut.fel.omo.semestral.manual;

/**
 * Represents a repository of manuals.
 */
public interface ManualRepo {
    /**
     * Gets a manual for a device.
     * @param deviceName name of the device
     * @return Manual
     */
    Manual getManual(String deviceName);
}
