package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import lombok.Getter;

/**
 * A sensor designed to capture and process various types of user inputs within the smart home system.
 * This sensor is responsible for detecting input actions like turning on a light or adjusting temperature, and communicating these actions to the system.
 */
@Getter
public class UserInputSensor extends Sensor {

    private UserInputType inputType;
    private Object inputValue; // Could be boolean, integer, double, etc., based on input type

    /**
     * Constructs a UserInputSensor with default settings.
     */
    public UserInputSensor() {
        super(DeviceState.OFF, 0, 0.0);
    }

    /**
     * Detects and records a user input, then notifies observers about the change.
     *
     * @param inputType The type of input detected, as defined in {@link UserInputType}.
     * @param inputValue The value associated with the input, which can vary based on the input type.
     */
    public void detectInput(UserInputType inputType, Object inputValue) {
        this.inputType = inputType;
        this.inputValue = inputValue;
        // Notifying observers about the input change
        notifyObservers();
    }
}

