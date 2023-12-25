package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

/**
 * Detects motion in the smart home, triggering automated responses in connected devices.
 */
@Getter
public class MotionSensor extends Sensor {
    private boolean motionDetected;

    public MotionSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    public void detectMotion(boolean isMotion) {
        // This method simulates the detection of motion.
        if (this.motionDetected != isMotion) {
            this.motionDetected = isMotion;
            notifyObservers();
        }
    }
}

