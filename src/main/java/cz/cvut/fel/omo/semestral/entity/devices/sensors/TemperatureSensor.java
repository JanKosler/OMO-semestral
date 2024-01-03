package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

/**
 * Represents a temperature sensor in the smart home simulation.
 * This sensor is responsible for measuring and reporting changes in temperature.
 */
@Getter
public class TemperatureSensor extends Sensor {
    private double currentTemperature;

    /**
     * Constructs a TemperatureSensor with default settings.
     */
    public TemperatureSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    /**
     * Reads and updates the current temperature.
     * Notifies observers if there is a change in the temperature.
     *
     * @param temperature The new temperature to be set.
     */
    public void readTemperature(double temperature) {
        // This method simulates reading the temperature.
        if (this.currentTemperature != temperature) {
            this.currentTemperature = temperature;
            notifyObservers();
        }
    }
}

