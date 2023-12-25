package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

/**
 * Manages the HVAC system in response to indoor and outdoor temperature readings,
 * as well as user input for desired target temperature. This controller ensures
 * optimal indoor climate control by adjusting the HVAC's operational state.
 * It decides whether to heat, cool, or ventilate based on the difference between
 * the indoor temperature and the target temperature, taking into account the
 * outdoor temperature. Specifically, if outdoor temperature is lower than indoor,
 * it opts for ventilation; otherwise, it initiates cooling.
 */
public class TemperatureController extends Controller {

    private final TemperatureSensor internalSensor;
    private final TemperatureSensor externalSensor;
    private final UserInputSensor userInputSensor;
    private final HVAC hvac;
    private double targetTemperature;
    private double indoorTemp;
    private double outdoorTemp;

    public TemperatureController(TemperatureSensor internalSensor, TemperatureSensor externalSensor, HVAC hvac, UserInputSensor userInputSensor) {
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.hvac = hvac;
        this.userInputSensor = userInputSensor;
        this.internalSensor.addObserver(this);
        this.externalSensor.addObserver(this);
        this.userInputSensor.addObserver(this);
    }

    @Override
    public void update(IDevice device) {
        if (device instanceof TemperatureSensor) {
            respondToSensor((Sensor) device);
        } else if (device == userInputSensor && userInputSensor.getInputType() == UserInputType.HVAC_TEMPERATURE) {
            Object input = userInputSensor.getInputValue();
            if (input instanceof Double) {
                setTargetTemperature((Double) input);
            }
        }
    }

    private void setTargetTemperature(double targetTemp) {
        this.targetTemperature = targetTemp;
        handleTemperature(); // React to new target temperature
    }

    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == internalSensor) {
            indoorTemp = ((TemperatureSensor) sensor).getCurrentTemperature();
            handleTemperature();
        } else if (sensor == externalSensor) {
            outdoorTemp = ((TemperatureSensor) sensor).getCurrentTemperature();
        }
    }

    private void handleTemperature() {
        if (Math.abs(indoorTemp - targetTemperature) > 1.0) {
            if (indoorTemp < targetTemperature) {
                hvac.executeCommand(DeviceCommand.SWITCH_TO_HEATING);
            } else if (indoorTemp > targetTemperature) {
                if (outdoorTemp > indoorTemp) {
                    hvac.executeCommand(DeviceCommand.SWITCH_TO_COOLING);
                } else {
                    hvac.executeCommand(DeviceCommand.SWITCH_TO_VENTILATION);
                }
            }
        } else {
            hvac.executeCommand(DeviceCommand.TURN_OFF);
        }
    }

}



