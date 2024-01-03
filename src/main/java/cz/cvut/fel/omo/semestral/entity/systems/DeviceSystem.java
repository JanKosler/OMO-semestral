package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

import java.util.List;

/**
 * Abstract class representing a generic device system in the smart home simulation.
 * This class serves as a base for specific types of device systems, providing common functionalities and properties.
 */
public abstract class DeviceSystem {


    private List<UserInputType> allowedUserInputTypes;

    /**
     * Retrieves the list of UserInputTypes that this device system is capable of handling.
     *
     * @return A list of UserInputType that the device system can process.
     */
    public List<UserInputType> getAllowedUserInputTypes() {
        return allowedUserInputTypes;
    }

    /**
     * Turns on the devices associated with this system.
     * This method should be overridden to provide specific turn-on behavior.
     */
    public void turnOn() {
        return;
    }

    /**
     * Turns off the devices associated with this system.
     * This method should be overridden to provide specific turn-off behavior.
     */
    public void turnOff() {
        return;
    }

    /**
     * Retrieves the appliance associated with this device system.
     *
     * @return An instance of Appliance associated with this system.
     */
    public Appliance getAppliance() {
        return null;
    }

    /**
     * Retrieves the UserInputSensor associated with this device system.
     * The sensor is used to detect and process user inputs for this system.
     *
     * @return An instance of UserInputSensor associated with this system.
     */
    public UserInputSensor getUserInputSensor() {
        return null;
    }
}
