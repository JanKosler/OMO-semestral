package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;


/**
 * Represents a motion sensor in the smart home system, capable of detecting movement within its vicinity.
 * The sensor is designed to trigger automated responses or alerts in the system upon motion detection.
 */
@Getter
public class MotionSensor extends Sensor {
    private boolean motionDetected;

    /**
     * Constructs a MotionSensor with default settings.
     */
    public MotionSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    /**
     * Simulates the detection of motion in the sensor's environment.
     * Notifies observers if there is a change in motion detection status.
     *
     * @param isMotion A boolean indicating whether motion has been detected.
     */
    public void detectMotion(boolean isMotion) {
        // This method simulates the detection of motion.
        if (this.motionDetected != isMotion) {
            this.motionDetected = isMotion;
            notifyObservers();
        }
    }
}

