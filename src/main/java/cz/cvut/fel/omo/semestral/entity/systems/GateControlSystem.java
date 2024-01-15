package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.GateController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.List;

/**
 * The {@code GateControlSystem} class represents a specific device system for managing a gate within the smart home simulation.
 * It includes a gate, a controller, and a user input sensor to control and monitor the gate's state and access.
 * This class extends the abstract {@link DeviceSystem} class and provides specific implementations for its methods.
 */
@Getter
public class GateControlSystem extends DeviceSystem {
    public final Gate gate;
    public final GateController controller;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.GATE_CONTROL);
    private final int deviceSystemID;

    /**
     * Creates a new GateControlSystem with the specified components.
     *
     * @param deviceSystemID   The unique ID of the device system.
     * @param gate             The gate associated with this system.
     * @param controller       The controller associated with this system.
     * @param userInputSensor  The user input sensor associated with this system.
     */
    public GateControlSystem(int deviceSystemID,Gate gate, GateController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.gate = gate;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the gate appliance associated with this GateControlSystem.
     *
     * @return The Gate appliance associated with this system.
     */
    @Override
    public Gate getAppliance() {
        return gate;
    }

    /**
     * Retrieves the controller associated with this GateControlSystem.
     *
     * @return The GateController associated with this system.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Retrieves the user input sensor associated with this GateControlSystem.
     *
     * @return The UserInputSensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Calculates and retrieves the total power consumption of the GateControlSystem.
     *
     * @return The total power consumption of the GateControlSystem.
     */
    @Override
    public double getTotalConsumption() {
        return gate.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    /**
     * Retrieves the list of devices associated with this GateControlSystem.
     *
     * @return A list of devices associated with this system, including the gate, controller, and user input sensor.
     */
    @Override
    public List<IDevice> getDevices() {
        return List.of(gate, controller, userInputSensor);
    }

    /**
     * Performs actions during each tick, including processing user input, updating the controller, and managing the gate.
     */
    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        gate.onTick();
    }

    /**
     * Returns a string representation of the GateControlSystem, including its deviceSystemID.
     *
     * @return A string representation of the GateControlSystem.
     */
    @Override
    public String toString() {
        return "GateControlSystem{" + "deviceSystemID=" + deviceSystemID +'}';
    }

    @Override
    public void setRoom(Room room) {
        return;
    }
}
