package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Alarm;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.SecuritySensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

import java.util.UUID;

/**
 * Controller for managing home security by interfacing with security sensors and alarms.
 * It reacts to alerts from SecuritySensor indicating potential security breaches
 * and handles user inputs to control the Alarm system. This controller is capable of
 * activating the Alarm in response to security threats and allows users to disarm the Alarm
 * through user input.
 */
public class SecurityController extends Controller {

    private final SecuritySensor securitySensor;
    private final UserInputSensor userInputSensor;
    private final Alarm alarm;

    private final double powerConsumptionPerTick = 1.75; //Consumption in mWh every 10 mins.

    /**
     * Constructs a SecurityController with a security sensor, user input sensor, and an alarm.
     *
     * @param securitySensor   The SecuritySensor that detects potential breaches.
     * @param userInputSensor  The UserInputSensor for receiving disarm commands for the alarm.
     * @param alarm            The Alarm system to be controlled.
     */
    public SecurityController(UUID serialNumber, SecuritySensor securitySensor, UserInputSensor userInputSensor, Alarm alarm) {
        super(serialNumber, 100);
        this.securitySensor = securitySensor;
        this.userInputSensor = userInputSensor;
        this.alarm = alarm;
        this.securitySensor.addObserver(this);
        this.userInputSensor.addObserver(this);
    }

    /**
     * Responds to updates from connected sensors (security or user input).
     * Determines whether to activate or disarm the alarm based on sensor inputs.
     *
     * @param device The device (either security sensor or user input sensor) reporting a change.
     */
    @Override
    public void update(IDevice device) {
        if (device instanceof SecuritySensor || device instanceof UserInputSensor) {
            respondToSensor((Sensor) device);
        }
    }

    @Override
    public void onTick() {
        if (this.getState() == DeviceState.ON) {
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Handles sensor updates for security detection and user inputs.
     * Activates the alarm on security threats and disarms it based on user commands.
     *
     * @param sensor The sensor reporting a change in security status or user input.
     */
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
            alarm.addtoActionPlan(DeviceCommand.TURN_ON); // Activate the alarm
        }
    }

    private void disarmAlarm() {
        alarm.addtoActionPlan(DeviceCommand.TURN_OFF); // Disarm the alarm
    }
}

