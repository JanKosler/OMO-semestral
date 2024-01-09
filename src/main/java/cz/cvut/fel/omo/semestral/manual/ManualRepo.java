package cz.cvut.fel.omo.semestral.manual;

/**
 * Represents a repository of manuals.
 */
public interface ManualRepo {
    Manual getManual(String deviceName);
}
