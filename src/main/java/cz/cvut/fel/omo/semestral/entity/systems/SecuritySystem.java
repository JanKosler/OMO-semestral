package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.SecurityController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.List;

/**
 * The {@code SecuritySystem} class represents a device system that manages security within a room in the smart home simulation.
 * It includes an alarm, a security sensor, a security controller, and a user input sensor for controlling and monitoring the security system.
 * This class extends the abstract {@link DeviceSystem} class and provides specific implementations for its methods.
 */
@Getter
public class SecuritySystem extends DeviceSystem {
    /** The alarm system device */
    public final Alarm alarm;
    /** The alarms system sensor */
    public final SecuritySensor securitySensor;
    /** The alarms system controller */
    public final SecurityController controller;
    /** The user input sensor */
    public final UserInputSensor userInputSensor; // For user inputs like arming/disarming the system
    /** The list of UserInputTypes that this system can process */
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.ALARM_DISARM);
    private final int deviceSystemID;

    /**
     * Creates a new SecuritySystem with the specified components.
     *
     * @param deviceSystemID  The unique ID of the device system.
     * @param alarm           The alarm device associated with this system.
     * @param securitySensor  The security sensor associated with this system.
     * @param controller      The security controller associated with this system.
     * @param userInputSensor The user input sensor associated with this system.
     */
    public SecuritySystem(int deviceSystemID, Alarm alarm, SecuritySensor securitySensor, SecurityController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.alarm = alarm;
        this.securitySensor = securitySensor;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the alarm device associated with this SecuritySystem.
     *
     * @return The alarm device associated with this system.
     */
    @Override
    public Alarm getAppliance() {
        return alarm;
    }

    /**
     * Retrieves the security controller associated with this SecuritySystem.
     *
     * @return The security controller associated with this system.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Retrieves the user input sensor associated with this SecuritySystem.
     *
     * @return The user input sensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Calculates and retrieves the total power consumption of the SecuritySystem, including the alarm, controller, and user input sensor.
     *
     * @return The total power consumption of the SecuritySystem.
     */
    @Override
    public double getTotalConsumption() {
        return alarm.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    /**
     * Retrieves the list of devices associated with this SecuritySystem, including the alarm, controller, and user input sensor.
     *
     * @return A list of devices associated with this system.
     */
    @Override
    public List<IDevice> getDevices() {
        return List.of(alarm, controller, userInputSensor);
    }

    /**
     * Performs actions during each tick, including reading the user input sensor, processing security sensor data, updating the controller, and managing the alarm.
     */
    @Override
    public void onTick() {
        userInputSensor.onTick();
        securitySensor.onTick();
        controller.onTick();
        alarm.onTick();
    }

    /**
     * Returns a string representation of the SecuritySystem, including its deviceSystemID.
     *
     * @return A string representation of the SecuritySystem.
     */
    @Override
    public String toString() {
        return "SecuritySystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    /**
     * Sets the room associated with this SecuritySystem (not implemented).
     *
     * @param room The room to associate with this system.
     */
    @Override
    public void setRoom(Room room) {
        return;
    }
}
