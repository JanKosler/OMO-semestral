package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;

// Outdoor temperature is used to determine whether to use cooling or ventilation
public class TemperatureController extends Controller {

    private final TemperatureSensor internalSensor;
    private final TemperatureSensor externalSensor;
    private final HVAC hvac; // Reference to the HVAC system
    private double targetTemperature;
    private double indoorTemp;
    private double outdoorTemp;

    public TemperatureController(TemperatureSensor internalSensor, TemperatureSensor externalSensor, HVAC hvac) {
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.hvac = hvac;
        this.internalSensor.addObserver(this);
        this.externalSensor.addObserver(this);
    }

    public void setTargetTemperature(double targetTemp) {
        this.targetTemperature = targetTemp;
    }

    @Override
    public void update(IDevice device) {
        if (device instanceof TemperatureSensor) {
            respondToSensor((Sensor) device);
        }
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



