package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import lombok.Getter;

import java.util.UUID;
/**
 * Fridge is an appliance that can be turned on and off and its internal
 * temperature can be adjusted.
 */
@Getter
public class Fridge extends Appliance {

    private double internalTemperature;
    private int capacity; // in liters

    public Fridge(UUID serialNumber, int capacity) {
        super(serialNumber);
        this.internalTemperature = 4.0; // Default temperature in Celsius
        this.capacity = capacity;
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                turnOff();
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


}

