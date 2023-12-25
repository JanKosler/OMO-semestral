package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import lombok.Getter;

/**
 * Captures and communicates various types of user inputs in the smart home system.
 */
@Getter
public class UserInputSensor extends Sensor {

    private UserInputType inputType;
    private Object inputValue; // Could be boolean, integer, double, etc., based on input type

    public UserInputSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    public void detectInput(UserInputType inputType, Object inputValue) {
        this.inputType = inputType;
        this.inputValue = inputValue;
        // Notifying observers about the input change
        notifyObservers();
    }
}

