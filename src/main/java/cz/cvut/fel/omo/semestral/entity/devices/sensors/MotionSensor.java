package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


/**
 * Represents a motion sensor in the smart home system, capable of detecting movement within its vicinity.
 * The sensor is designed to trigger automated responses or alerts in the system upon motion detection.
 */
@Getter
@Slf4j
public class MotionSensor extends Sensor {
    /** Indicates whether motion has been detected by the sensor */
    private boolean motionDetected;
    private final double powerConsumptionPerTick = 1.75 / 600.00; //Consumption in mWh every 10 mins.
    /** The maximum wear that this sensor can sustain before breaking */
    private final int wearCapacity = 100;
    /** The room in which this sensor is located */
    @Setter
    private Room room;

    /**
     * Constructs a MotionSensor with default settings.
     */
    public MotionSensor(UUID serialNumber, Room room) {
        super(serialNumber, 100);
        this.room = room;
    }

    @Override
    public void onTick() {
        if (this.getState() == DeviceState.ON) {
            detectMotion();
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Simulates the detection of motion in the sensor's environment.
     * Notifies observers if there is a change in motion detection status.
     *
     */
    public void detectMotion() {
        // This method simulates the detection of motion.
        boolean isMotion = !room.getInhabitants().isEmpty();
        if (isMotion != motionDetected) {
            motionDetected = isMotion;
            notifyObservers();
        }
    }

}

