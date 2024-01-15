package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Fridge;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.FridgeController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class FridgeSystem extends DeviceSystem {
    public final Fridge fridge;
    public final FridgeController controller;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.FRIDGE_TEMPERATURE);
    private final int deviceSystemID;

    /**
     * Creates a new FridgeSystem.
     *
     * @param deviceSystemID   The ID of the device system.
     * @param fridge           The fridge associated with this system.
     * @param controller       The controller associated with this system.
     * @param userInputSensor  The user input sensor associated with this system.
     */
    public FridgeSystem(int deviceSystemID, Fridge fridge, FridgeController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.fridge = fridge;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the controller associated with this FridgeSystem.
     *
     * @return The FridgeController associated with this system.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Retrieves the fridge appliance associated with this FridgeSystem.
     *
     * @return The Fridge appliance associated with this system.
     */
    @Override
    public Fridge getAppliance() {
        return fridge;
    }

    /**
     * Retrieves the user input sensor associated with this FridgeSystem.
     *
     * @return The UserInputSensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Calculates and retrieves the total power consumption of the FridgeSystem.
     *
     * @return The total power consumption of the FridgeSystem.
     */
    @Override
    public double getTotalConsumption() {
        return fridge.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    /**
     * Retrieves the list of devices associated with this FridgeSystem.
     *
     * @return A list of devices associated with this system, including the fridge, controller, and user input sensor.
     */
    @Override
    public List<IDevice> getDevices() {
        return List.of(fridge, controller, userInputSensor);
    }

    /**
     * Performs actions during each tick, including processing user input, updating the controller, and managing the fridge.
     */
    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        fridge.onTick();
    }
    /**
     * Returns a string representation of the FridgeSystem, including its deviceSystemID.
     *
     * @return A string representation of the FridgeSystem.
     */
    @Override
    public String toString() {
        return "FridgeSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    /**
     * Sets the room associated with this FridgeSystem.
     * Since a fridge system doesn't directly relate to a room, this method does nothing.
     *
     * @param room The room to associate with this system.
     */
    @Override
    public void setRoom(Room room) {
        return;
    }
}
