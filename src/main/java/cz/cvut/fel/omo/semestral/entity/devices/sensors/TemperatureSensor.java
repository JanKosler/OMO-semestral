package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Temperature;
import lombok.Getter;

import java.util.Random;
import java.util.UUID;

/**
 * Represents a temperature sensor in the smart home simulation.
 * This sensor is responsible for measuring and reporting changes in temperature.
 */
@Getter
public class TemperatureSensor extends Sensor {
    /** The current temperature measured by the sensor */
    private double currentTemperature;
    private final double powerConsumptionPerTick = 0.75 / 600.00; //Consumption in mWh every 10 mins.
    /** The maximum wear that this sensor can sustain before breaking */
    private final int wearCapacity = 100;
    /** The temperature object that this sensor is associated with */
    private final Temperature temperature;


    /**
     * Constructs a TemperatureSensor with default settings.
     */
    public TemperatureSensor(UUID serialNumber, Temperature temperature) {
        super(serialNumber, new Random().nextInt(250)+100);
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

