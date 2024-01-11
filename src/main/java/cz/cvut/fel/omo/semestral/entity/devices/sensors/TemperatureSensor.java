package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.Temperature;
import lombok.Getter;

/**
 * Represents a temperature sensor in the smart home simulation.
 * This sensor is responsible for measuring and reporting changes in temperature.
 */
@Getter
public class TemperatureSensor extends Sensor {
    private double currentTemperature;
    private final double powerConsumptionPerTick = 0.75; //Consumption in mWh every 10 mins.
    private final int wearCapacity = 100;
    private final Temperature temperature;


    /**
     * Constructs a TemperatureSensor with default settings.
     */
    public TemperatureSensor(Temperature temperature) {
        super(100);
        this.currentTemperature = temperature.getTemperature();
        this.temperature = temperature;
    }

    @Override
    public void onTick() {
        if (this.getState() == DeviceState.ON) {
            readTemperature();
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Reads and updates the current temperature.
     * Notifies observers if there is a change in the temperature.
     *
     */
    public void readTemperature() {
        double temperature = this.temperature.getTemperature();
        // This method simulates reading the temperature.
        if (this.currentTemperature != temperature) {
            this.currentTemperature = temperature;
            notifyObservers();
        }
    }
}

