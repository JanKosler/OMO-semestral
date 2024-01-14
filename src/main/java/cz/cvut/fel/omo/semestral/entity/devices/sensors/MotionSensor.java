package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.UUID;


/**
 * Represents a motion sensor in the smart home system, capable of detecting movement within its vicinity.
 * The sensor is designed to trigger automated responses or alerts in the system upon motion detection.
 */
@Getter
public class MotionSensor extends Sensor {
    private boolean motionDetected;
    private final double powerConsumptionPerTick = 1.75; //Consumption in mWh every 10 mins.
    private final int wearCapacity = 100;
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
        if (this.motionDetected != isMotion) {
            this.motionDetected = isMotion;
            notifyObservers();
        }
    }
}

