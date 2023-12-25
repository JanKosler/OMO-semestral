package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

/**
 * Manages home security by responding to SecuritySensor alerts and user inputs.
 * Activates an Alarm in case of security breaches and allows users to disarm it.
 */
public class SecurityController extends Controller {

    private final SecuritySensor securitySensor;
    private final UserInputSensor userInputSensor;
    private final Alarm alarm;

    public SecurityController(SecuritySensor securitySensor, UserInputSensor userInputSensor, Alarm alarm) {
        this.securitySensor = securitySensor;
        this.userInputSensor = userInputSensor;
        this.alarm = alarm;
        this.securitySensor.addObserver(this);
        this.userInputSensor.addObserver(this);
    }

    @Override
    public void update(IDevice device) {
        if (device instanceof SecuritySensor || device instanceof UserInputSensor) {
            respondToSensor((Sensor) device);
        }
    }

    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == securitySensor) {
            handleSecuritySensor();
        } else if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.ALARM_DISARM) {
            disarmAlarm();
        }
    }

    private void handleSecuritySensor() {
        if (securitySensor.isBreachDetected()) {
            alarm.executeCommand(DeviceCommand.TURN_ON); // Activate the alarm
        }
    }

    private void disarmAlarm() {
        alarm.executeCommand(DeviceCommand.TURN_OFF); // Disarm the alarm
    }
}

