package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TemperatureController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.List;

@Getter
/**
 * The {@code HVACSystem} class represents a specific device system for managing an HVAC (Heating, Ventilation, and Air Conditioning) system within the smart home simulation.
 * It includes an HVAC appliance, a temperature controller, internal and external temperature sensors, and a user input sensor to control and monitor the HVAC system's temperature settings.
 * This class extends the abstract {@link DeviceSystem} class and provides specific implementations for its methods.
 */
public class HVACSystem extends DeviceSystem {
    public final HVAC hvac;
    public final TemperatureController controller;
    public final TemperatureSensor internalSensor;
    public final TemperatureSensor externalSensor;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.HVAC_TEMPERATURE);
    private final int deviceSystemID;

    /**
     * Creates a new HVACSystem with the specified components.
     *
     * @param deviceSystemID   The unique ID of the device system.
     * @param hvac             The HVAC appliance associated with this system.
     * @param controller       The temperature controller associated with this system.
     * @param internalSensor   The internal temperature sensor associated with this system.
     * @param externalSensor   The external temperature sensor associated with this system.
     * @param userInputSensor  The user input sensor associated with this system.
     */
    public HVACSystem(int deviceSystemID,HVAC hvac, TemperatureController controller, TemperatureSensor internalSensor, TemperatureSensor externalSensor, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.hvac = hvac;
        this.controller = controller;
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the HVAC appliance associated with this HVACSystem.
     *
     * @return The HVAC appliance associated with this system.
     */
    @Override
    public HVAC getAppliance() {
        return hvac;
    }

    /**
     * Retrieves the temperature controller associated with this HVACSystem.
     *
     * @return The temperature controller associated with this system.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Retrieves the user input sensor associated with this HVACSystem.
     *
     * @return The user input sensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Calculates and retrieves the total power consumption of the HVACSystem, including all its components.
     *
     * @return The total power consumption of the HVACSystem.
     */
    @Override
    public double getTotalConsumption() {
        return hvac.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption() + internalSensor.getTotalPowerConsumption() + externalSensor.getTotalPowerConsumption();
    }

    /**
     * Retrieves the list of devices associated with this HVACSystem, including the HVAC appliance, controller, and sensors.
     *
     * @return A list of devices associated with this system.
     */
    @Override
    public List<IDevice> getDevices() {
        return List.of(hvac, controller, userInputSensor, internalSensor, externalSensor);
    }

    /**
     * Performs actions during each tick, including reading temperature sensors, processing user input, updating the controller, and managing the HVAC system.
     */
    @Override
    public void onTick() {
        internalSensor.onTick();
        externalSensor.onTick();
        userInputSensor.onTick();
        controller.onTick();
        hvac.onTick();
    }

    /**
     * Returns a string representation of the HVACSystem, including its deviceSystemID.
     *
     * @return A string representation of the HVACSystem.
     */
    @Override
    public String toString() {
        return "HVACSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    /**
     * Sets the room associated with this HVACSystem.
     * Since an HVAC system doesn't directly relate to a room, this method does nothing.
     *
     * @param room The room to associate with this system.
     */
    @Override
    public void setRoom(Room room) {
        return;
    }
}
