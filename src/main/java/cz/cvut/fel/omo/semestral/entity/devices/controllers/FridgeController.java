package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Fridge;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Controller dedicated to managing a Fridge appliance within the smart home system.
 * This controller is responsible for adjusting the internal temperature of the fridge
 * based on user inputs. It listens to a UserInputSensor for temperature settings and
 * modifies the fridge's temperature accordingly. The temperature adjustment process
 * involves incrementally changing the temperature until it reaches the user-defined target.
 */
@Slf4j
public class FridgeController extends Controller {
    /** The fridge appliance that this controller manages */
    private final Fridge fridge;
    /** The sensor that detects user inputs for the fridge */
    private final UserInputSensor userInputSensor;


    private final double powerConsumptionPerTick = 1.75; //Consumption in mWh every 10 mins.

    /**
     * Constructs a FridgeController with a specific fridge and user input sensor.
     *
     * @param fridge The fridge appliance that this controller manages.
     * @param userInputSensor The sensor that detects user inputs for the fridge.
     */
    public FridgeController(UUID serialNumber, Fridge fridge, UserInputSensor userInputSensor) {
        super(serialNumber, 100);
        this.fridge = fridge;
        this.userInputSensor = userInputSensor;
        this.userInputSensor.addObserver(this);
    }

    /**
     * Responds to updates from the connected UserInputSensor.
     * When the sensor detects a change in the desired fridge temperature, this method
     * adjusts the fridge's temperature accordingly.
     *
     * @param device The device (sensor) that has detected a change.
     */
    @Override
    public void update(IDevice device) {
        if (device instanceof UserInputSensor) {
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
     * Responds to changes detected by sensors.
     * Specifically handles changes in desired fridge temperature.
     *
     * @param sensor The sensor that has detected a change.
     */
    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.FRIDGE_TEMPERATURE) {
            Object input = userInputSensor.getInputValue();
            if (input instanceof Double) {
                setFridgeTemperature((Double) input);
            }
        }
    }

    /**
     * Adjusts the fridge's internal temperature to the target temperature.
     * This method incrementally changes the temperature until the target is reached.
     *
     * @param targetTemperature The target temperature to set the fridge to.
     */
    private void setFridgeTemperature(double targetTemperature) {
        double currentTemperature = fridge.getInternalTemperature();

        // Calculate the difference and send appropriate commands to adjust temperature
        while (currentTemperature < targetTemperature) {
            fridge.addtoActionPlan(DeviceCommand.INCREASE_TEMPERATURE);
            currentTemperature++;
        }
        while (currentTemperature > targetTemperature) {
            fridge.addtoActionPlan(DeviceCommand.DECREASE_TEMPERATURE);
            currentTemperature--;
        }
        log.info("Controller: Fridge temperature set to {}.", targetTemperature);
    }
}

