package cz.cvut.fel.omo.semestral.entity.devices;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.manual.Manual;
import cz.cvut.fel.omo.semestral.tick.Tickable;

import java.util.UUID;

/**
 * Interface representing a general device in the smart home simulation.
 * This interface defines the common functionalities that all devices must implement.
 */
public interface IDevice{

    /**
     * Retrieves the current state of the device.
     *
     * @return The current state of the device, as defined in {@link DeviceState}.
     */
    DeviceState getState();

    /**
     * Retrieves the total wear of the device.
     * Wear could be a measure of how much the device has been used or its age.
     *
     * @return The total wear value of the device.
     */
    int getTotalWear();

    /**
     * Retrieves the power consumption of the device.
     *
     * @return The power consumption value of the device.
     */
    double getTotalPowerConsumption();

    /**
     * Sets the state of the device.
     *
     * @param state The new state to set for the device, as defined in {@link DeviceState}.
     */
    void setState(DeviceState state);

    /**
     * Turns on the device.
     * Implementing classes will define the specific behavior when the device is turned on.
     */
    void turnOn();

    /**
     * Turns off the device.
     * Implementing classes will define the specific behavior when the device is turned off.
     */
    void turnOff();

    /**
     * Updates the total power consumption of the device.
     *
     * @param powerConsumption The power consumption value to update.
     */
    void updatePowerConsumption(double powerConsumption);

    /**
     * Updates the total wear of the device.
     *
     * @param wear The wear value to update.
     */
    void updateWear(int wear);

    /**
     * Checks if the device is broken and updates its state if necessary.
     */
    void checkIfBroken();

    /**
     * Adds a malfunction observer to the device.
     *
     * @param observer The observer to be added.
     */
    void addMalfunctionObserver(DeviceMalfunctionObserver observer);

    /**
     * Notifies all malfunction observers about the device malfunction.
     */
    void notifyMalfunctionObservers();

    /**
     * Retrieves the serial number of the device.
     *
     * @return The serial number of the device.
     */
    UUID getSerialNumber();

    /**
     * Repairs the device without using a manual.
     */
    void repair();

    /**
     * Repairs the device using a manual.
     *
     * @param manual The manual to be used for repair.
     */
    void repair(Manual manual);

}
