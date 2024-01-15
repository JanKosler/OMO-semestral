package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.Random;
import java.util.UUID;


/**
 * Represents a security sensor within the smart home system, tasked with monitoring security breaches.
 * The sensor can detect security-related incidents and trigger appropriate alerts or responses within the system.
 */
@Getter
public class SecuritySensor extends Sensor {
    /** Indicates whether a security breach has been detected by the sensor */
    private boolean breachDetected;
    private final double powerConsumptionPerTick = 1.25 / 600.00; //Consumption in mWh every 10 mins.
    /** The maximum wear that this sensor can sustain before breaking */
    private final int wearCapacity = 100;

    /**
     * Constructs a SecuritySensor with default settings.
     */
    public SecuritySensor(UUID serialNumber) {
        super(serialNumber, new Random().nextInt(250)+100);
    }

    /**
     * Performs actions during each tick.
     * Updates wear, power consumption, and checks for malfunctions.
     */
    @Override
    public void onTick() {
        if (this.getState() == DeviceState.ON) {
            detectBreach(true);
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Simulates the detection of a security breach.
     * Notifies observers if the state of breach detection changes.
     *
     * @param isBreach A boolean indicating whether a breach has been detected.
     */
    public void detectBreach(boolean isBreach) {
        // This method simulates the detection of a security breach.
        if (this.breachDetected != isBreach) {
            this.breachDetected = isBreach;
            notifyObservers();
        }
    }
}

