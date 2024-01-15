package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
/**
 * Represents a Fridge appliance with adjustable temperature settings.
 * This class allows for the control of the fridge's power state and internal temperature,
 * and it handles various commands related to these functionalities.
 */
@Getter
@Slf4j
public class Fridge extends Appliance {
    /** The internal temperature of the fridge in Celsius */
    private double internalTemperature;
    /** The power consumption of the fridge per tick */
    private final double powerConsumptionPerTick = (double) 30 / 600;

    /**
     * Constructs a new Fridge with the specified serial number.
     * The fridge is initialized with a default internal temperature of 4.0°C.
     *
     * @param serialNumber The unique identifier for this fridge appliance.
     */
    public Fridge(UUID serialNumber) {
        super(serialNumber, 500);
        this.internalTemperature = 4.0; // Default temperature in Celsius
    }

    /**
     * Executes a specific command on the fridge appliance.
     * This method handles commands to turn the fridge on or off, and to adjust its temperature.
     *
     * @param command The device command to be executed.
     */
    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                setIdle();
                break;
            case INCREASE_TEMPERATURE:
                adjustTemperature(internalTemperature + 1);
                break;
            case DECREASE_TEMPERATURE:
                adjustTemperature(internalTemperature - 1);
                break;
            default:
                System.out.println("Command not recognized for Fridge.");
                break;
        }
    }

    /**
     * Performs actions on each tick based on the current state of the fridge.
     * This method updates the wear and power consumption of the fridge, and checks if it's broken.
     */
    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if (currentState != DeviceState.OFF && currentState != DeviceState.MALFUNCTION) {
            performAllActions();
            updateWear(5);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }
    /**
     * Adjusts the internal temperature of the fridge.
     * This method sets the temperature within a safe range between 1°C and
     5°C.

     less
     Copy code
     * @param newTemperature The desired temperature to set the fridge to.
     */
    private void adjustTemperature(double newTemperature) {
        // Define the safe temperature range
        final double minTemperature = 1.0; // 1°C
        final double maxTemperature = 5.0; // 5°C

        // Ensure the new temperature is within the range
        if (newTemperature < minTemperature) {
            this.internalTemperature = minTemperature;
        } else if (newTemperature > maxTemperature) {
            this.internalTemperature = maxTemperature;
        } else {
            this.internalTemperature = newTemperature;
        }

        System.out.println("Fridge temperature set to " + this.internalTemperature + "°C");
    }

    /**
     * Sets the fridge to idle state.
     * This method changes the state of the fridge to ON, indicating it's functioning but not actively cooling.
     */
    @Override
    public void setIdle() {
        this.setState(DeviceState.ON);
    }


}

