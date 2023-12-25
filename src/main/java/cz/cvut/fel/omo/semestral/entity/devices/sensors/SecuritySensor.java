package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

@Getter
public class SecuritySensor extends Sensor {
    private boolean breachDetected;

    public SecuritySensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    public void detectBreach(boolean isBreach) {
        // This method simulates the detection of a security breach.
        if (this.breachDetected != isBreach) {
            this.breachDetected = isBreach;
            notifyObservers();
        }
    }
}

