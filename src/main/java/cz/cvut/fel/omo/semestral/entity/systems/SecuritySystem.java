package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.SecurityController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

public class SecuritySystem extends DeviceSystem {
    public final Alarm alarm;
    public final SecuritySensor securitySensor;
    public final SecurityController controller;
    public final UserInputSensor userInputSensor; // For user inputs like arming/disarming the system

    public SecuritySystem(Alarm alarm, SecuritySensor securitySensor, SecurityController controller, UserInputSensor userInputSensor) {
        this.alarm = alarm;
        this.securitySensor = securitySensor;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
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
        alarm.turnOn();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        alarm.turnOff();
    }
}
