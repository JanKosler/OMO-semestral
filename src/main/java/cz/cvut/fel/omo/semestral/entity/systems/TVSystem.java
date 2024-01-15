package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TVController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.List;

/**
 * The {@code TVSystem} class represents a device system that manages a TV, its controller, and a user input sensor in a room of the smart home simulation.
 * It allows users to control and monitor the TV, change channels, adjust volume, and power on/off the TV.
 * This class extends the abstract {@link DeviceSystem} class and provides specific implementations for its methods.
 */
@Getter
public class TVSystem extends DeviceSystem {
    /** The TV device */
    public final TV tv;
    /** The TV controller */
    public final TVController controller;
    /** The user input sensor */
    public final UserInputSensor userInputSensor;
    /** The list of UserInputTypes that this system can process */
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.TV_VOLUME, UserInputType.TV_CHANNEL, UserInputType.TV_POWER);
    private final int deviceSystemID;

    /**
     * Creates a new TVSystem with the specified components.
     *
     * @param deviceSystemID  The unique ID of the device system.
     * @param tv              The TV device associated with this system.
     * @param controller      The TV controller associated with this system.
     * @param userInputSensor The user input sensor associated with this system.
     */
    public TVSystem(int deviceSystemID, TV tv, TVController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.tv = tv;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the TV associated with this system.
     * @return An instance of TV associated with this system.
     */
    @Override
    public TV getAppliance() {
        return tv;
    }

    /**
     * Retrieves the TV controller associated with this TVSystem.
     *
     * @return The TV controller associated with this system.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Retrieves the UserInputSensor associated with this system.
     * @return An instance of UserInputSensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Calculates and retrieves the total power consumption of the TVSystem, including the TV, controller, and user input sensor.
     *
     * @return The total power consumption of the TVSystem.
     */
    @Override
    public double getTotalConsumption() {
        return tv.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    /**
     * Retrieves the list of devices associated with this TVSystem, including the TV, controller, and user input sensor.
     *
     * @return A list of devices associated with this system.
     */
    @Override
    public List<IDevice> getDevices() {
        return List.of(tv, controller, userInputSensor);
    }

    /**
     * Performs actions during each tick, including reading the user input sensor, updating the controller, and managing the TV.
     */
    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        tv.onTick();
    }

    /**
     * Returns a string representation of the TVSystem, including its deviceSystemID.
     *
     * @return A string representation of the TVSystem.
     */
    @Override
    public String toString() {
        return "TVSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }


    @Override
    public void setRoom(Room room) {
        return;
    }
}
