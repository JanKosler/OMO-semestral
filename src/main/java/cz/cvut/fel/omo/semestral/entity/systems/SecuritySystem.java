package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.SecurityController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;

/**
 * SecuritySystem is a system that holds an alarm, its controller and a user input sensor.
 * @see DeviceSystem base class
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

    public SecuritySystem(Alarm alarm, SecuritySensor securitySensor, SecurityController controller, UserInputSensor userInputSensor) {
        this.alarm = alarm;
        this.securitySensor = securitySensor;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    @Override
    public Alarm getAppliance() {
        return alarm;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public void turnOn() {
        userInputSensor.turnOn();
        controller.turnOn();
        alarm.setIdle();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        alarm.turnOff();
    }

    @Override
    public void onTick() {
        securitySensor.onTick();
        userInputSensor.onTick();
        controller.onTick();
        alarm.onTick();
    }
}
