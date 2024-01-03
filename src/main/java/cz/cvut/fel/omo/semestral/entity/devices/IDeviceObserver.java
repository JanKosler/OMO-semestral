package cz.cvut.fel.omo.semestral.entity.devices;

/**
 * Interface for implementing observer functionality in device systems.
 * This interface allows objects to be notified of changes in device states.
 */
public interface IDeviceObserver {

    /**
     * Updates the observer based on a change in the observed device's state.
     *
     * @param device The device that has undergone a change and is notifying its observers.
     */
    void update(IDevice device);
}
