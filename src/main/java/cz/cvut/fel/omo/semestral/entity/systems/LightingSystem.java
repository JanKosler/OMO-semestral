package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Light;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.LightController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
/**
 * The {@code LightingSystem} class represents a specific device system for managing lighting within a room in the smart home simulation.
 * It includes a list of lights, a light controller, a motion sensor, and a user input sensor to control and monitor the lighting system.
 * This class extends the abstract {@link DeviceSystem} class and provides specific implementations for its methods.
 */
public class LightingSystem extends DeviceSystem {
    public final List<Light> lights;
    public final LightController controller;
    public final MotionSensor motionSensor;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.LIGHT_SWITCH);
    private final int deviceSystemID;

    /**
     * Creates a new LightingSystem with the specified components.
     *
     * @param deviceSystemID  The unique ID of the device system.
     * @param lights          The list of lights associated with this system.
     * @param controller      The light controller associated with this system.
     * @param motionSensor    The motion sensor associated with this system.
     * @param userInputSensor The user input sensor associated with this system.
     */
    public LightingSystem(int deviceSystemID,List<Light> lights, LightController controller, MotionSensor motionSensor, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.lights = lights;
        this.controller = controller;
        this.motionSensor = motionSensor;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the first light in the list of lights associated with this LightingSystem.
     *
     * @return The first light associated with this system.
     */
    @Override
    public Light getAppliance() {
            return lights.get(0);
        }

    /**
    * Retrieves the user input sensor associated with this LightingSystem.
    *
    * @return The user input sensor associated with this system.
    */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
    * Retrieves the light controller associated with this LightingSystem.
    *
    * @return The light controller associated with this system.
    */
    @Override
    public Controller getController() {
            return controller;
        }

    /**
    * Retrieves the motion sensor associated with this LightingSystem.
    *
    * @return The motion sensor associated with this system.
    */
    @Override
    public MotionSensor getMotionSensor() {
            return motionSensor;
        }

    /**
    * Calculates and retrieves the total power consumption of the LightingSystem, including all its lights, controller, and user input sensor.
    *
    * @return The total power consumption of the LightingSystem.
    */
    @Override
    public double getTotalConsumption() {
        return lights.stream().mapToDouble(Light::getTotalPowerConsumption).sum() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    /**
    * Retrieves the list of devices associated with this LightingSystem, including the lights, controller, motion sensor, and user input sensor.
    *
    * @return A list of devices associated with this system.
    */
    @Override
    public List<IDevice> getDevices() {
        List<IDevice> devices = new ArrayList<>(lights);
        devices.add(controller);
        devices.add(userInputSensor);
        devices.add(motionSensor);
        return devices;
    }

    /**
    * Performs actions during each tick, including reading the motion sensor, processing user input, updating the controller, and managing the lights.
    */
    @Override
    public void onTick() {
        motionSensor.onTick();
        userInputSensor.onTick();
        controller.onTick();
        lights.forEach(Light::onTick);
    }

    /**
    * Returns a string representation of the LightingSystem, including its deviceSystemID.
    *
    * @return A string representation of the LightingSystem.
    */
    @Override
    public String toString() {
            return "LightingSystem{" + "deviceSystemID=" + deviceSystemID + '}';
        }

    @Override
    public void setRoom(Room room) {
        motionSensor.setRoom(room);
    }
}
