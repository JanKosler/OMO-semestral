package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

@Getter
public class UserInputSensor extends Sensor {
    private boolean inputDetected;

    public UserInputSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    public void detectInput(boolean isInput) {
        // This method simulates the detection of user input.
        if (this.inputDetected != isInput) {
            this.inputDetected = isInput;
            notifyObservers();
        }
    }
}

