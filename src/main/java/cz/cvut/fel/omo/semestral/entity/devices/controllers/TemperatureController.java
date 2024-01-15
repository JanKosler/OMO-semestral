package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.ControllerRecord;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.states.CoolingState;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.states.HVACState;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.states.HeatingState;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.states.VentilationState;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

/**
 * Controller responsible for managing the HVAC system in the smart home, taking into account both
 * indoor and outdoor temperatures as well as user-defined target temperatures.
 * The TemperatureController dynamically adjusts the operation of the HVAC system
 * to maintain comfortable indoor conditions, choosing the appropriate action (heating, cooling, or ventilating)
 * based on the comparison of indoor temperature with the target, and considering the outdoor temperature.
 * When outdoor temperatures are lower than indoor, it opts for ventilation; otherwise, it engages cooling.
 */
@Slf4j
public class TemperatureController extends Controller {
    /** The sensor that monitors indoor temperature */
    private final TemperatureSensor internalSensor;
    /** The sensor that monitors outdoor temperature */
    private final TemperatureSensor externalSensor;
    /** The sensor that receives user inputs for the target temperature */
    private final UserInputSensor userInputSensor;
    /** The HVAC system that this controller manages */
    private final HVAC hvac;
    /** The target temperature for indoor climate control */
    private double targetTemperature;
    /** The current indoor temperature */
    private double indoorTemp;
    /** The current outdoor temperature */
    private double outdoorTemp;

    private final double powerConsumptionPerTick = 1.75 / 600.00; //Consumption in mWh every 10 mins.

    /**
     * Constructs a TemperatureController with specific temperature sensors and HVAC system.
     *
     * @param internalSensor  The sensor monitoring indoor temperature.
     * @param externalSensor  The sensor monitoring outdoor temperature.
     * @param hvac            The HVAC system to control.
     * @param userInputSensor The sensor for receiving user inputs regarding target temperature.
     */
    public TemperatureController(UUID serialNumber, TemperatureSensor internalSensor, TemperatureSensor externalSensor, HVAC hvac, UserInputSensor userInputSensor) {
        super(serialNumber, new Random().nextInt(250)+100);
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.hvac = hvac;
        this.userInputSensor = userInputSensor;
        this.internalSensor.addObserver(this);
        this.externalSensor.addObserver(this);
        this.userInputSensor.addObserver(this);
        this.targetTemperature = internalSensor.getCurrentTemperature();
        this.indoorTemp = internalSensor.getCurrentTemperature();
        this.outdoorTemp = externalSensor.getCurrentTemperature();
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
     * Performs actions during each tick.
     * Updates wear, power consumption, and checks for malfunctions.
     */
    @Override
    public void onTick() {
        setTickCounter(getTickCounter() + 1);
        if (this.getState() == DeviceState.ON) {
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
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
        if (Math.abs(indoorTemp - targetTemperature) > 0.0) {
            if (indoorTemp < targetTemperature && !(hvac.getCurrentState() instanceof HeatingState)) {
                hvac.addtoActionPlan(DeviceCommand.SWITCH_TO_HEATING);
                log.info("Controller: Switching to heating, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp);
                this.records.add(new ControllerRecord(this.getTickCounter(),this, "Switching to heating, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp));
            } else if (indoorTemp > targetTemperature) {
                if (outdoorTemp > targetTemperature) {
                    if (!(hvac.getCurrentState() instanceof CoolingState)){
                        hvac.addtoActionPlan(DeviceCommand.SWITCH_TO_COOLING);
                        log.info("Controller: Switching to cooling, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp);
                        records.add(new ControllerRecord(this.getTickCounter(),this, "Switching to cooling, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp));
                    }
                } else if (!(hvac.getCurrentState() instanceof VentilationState)) {
                    hvac.addtoActionPlan(DeviceCommand.SWITCH_TO_VENTILATION);
                    log.info("Controller: Switching to ventilation, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp);
                    records.add(new ControllerRecord(this.getTickCounter(),this, "Switching to ventilation, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp));
                }
            }
        } else {
            hvac.addtoActionPlan(DeviceCommand.TURN_OFF);
            log.info("Controller: HVAC turned off, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp);
            records.add(new ControllerRecord(this.getTickCounter(),this, "HVAC turned off, target: " + targetTemperature + ", current: " + indoorTemp + ", outdoor: " + outdoorTemp));
        }
    }

}



