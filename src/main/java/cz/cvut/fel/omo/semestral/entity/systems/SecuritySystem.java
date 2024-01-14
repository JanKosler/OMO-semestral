package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.SecurityController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
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
    private final int deviceSystemID;

    public SecuritySystem(int deviceSystemID, Alarm alarm, SecuritySensor securitySensor, SecurityController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
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
    public double getTotalConsumption() {
        return alarm.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    @Override
    public List<IDevice> getDevices() {
        return List.of(alarm, controller, userInputSensor);
    }

    @Override
    public void onTick() {
        userInputSensor.onTick();
        securitySensor.onTick();
        controller.onTick();
        alarm.onTick();
    }
}
