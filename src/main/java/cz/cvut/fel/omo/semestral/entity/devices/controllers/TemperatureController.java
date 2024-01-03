package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

/**
 * Controller responsible for managing the HVAC system in the smart home, taking into account both
 * indoor and outdoor temperatures as well as user-defined target temperatures.
 * The TemperatureController dynamically adjusts the operation of the HVAC system
 * to maintain comfortable indoor conditions, choosing the appropriate action (heating, cooling, or ventilating)
 * based on the comparison of indoor temperature with the target, and considering the outdoor temperature.
 * When outdoor temperatures are lower than indoor, it opts for ventilation; otherwise, it engages cooling.
 */
public class TemperatureController extends Controller {

    private final TemperatureSensor internalSensor;
    private final TemperatureSensor externalSensor;
    private final UserInputSensor userInputSensor;
    private final HVAC hvac;
    private double targetTemperature;
    private double indoorTemp;
    private double outdoorTemp;

    /**
     * Constructs a TemperatureController with specific temperature sensors and HVAC system.
     *
     * @param internalSensor  The sensor monitoring indoor temperature.
     * @param externalSensor  The sensor monitoring outdoor temperature.
     * @param hvac            The HVAC system to control.
     * @param userInputSensor The sensor for receiving user inputs regarding target temperature.
     */
    public TemperatureController(TemperatureSensor internalSensor, TemperatureSensor externalSensor, HVAC hvac, UserInputSensor userInputSensor) {
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.hvac = hvac;
        this.userInputSensor = userInputSensor;
        this.internalSensor.addObserver(this);
        this.externalSensor.addObserver(this);
        this.userInputSensor.addObserver(this);
    }

    /**
     * Responds to updates from the connected sensors.
     * Adjusts the HVAC system based on temperature readings and user inputs.
     *
     * @param device The device (sensor) reporting a change.
     */
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

    /**
     * Sets the target temperature for indoor climate control.
     * Triggers the process to adjust the HVAC system towards this temperature.
     *
     * @param targetTemp The desired target temperature set by the user.
     */
    private void setTargetTemperature(double targetTemp) {
        this.targetTemperature = targetTemp;
        handleTemperature(); // React to new target temperature
    }

    /**
     * Handles temperature control based on current readings from sensors.
     * Decides the mode of HVAC operation (heating, cooling, ventilation) as required.
     */
    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == internalSensor) {
            indoorTemp = ((TemperatureSensor) sensor).getCurrentTemperature();
            handleTemperature();
        } else if (sensor == externalSensor) {
            outdoorTemp = ((TemperatureSensor) sensor).getCurrentTemperature();
        }
    }

    /**
     * Handles the logic for adjusting the HVAC system based on the current and target temperatures.
     * This involves deciding whether to heat, cool, or ventilate the home to achieve the target temperature.
     * If the outdoor temperature is lower than the indoor temperature, the system will opt for ventilation.
     */
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



