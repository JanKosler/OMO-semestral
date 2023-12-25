package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents a Light in the smart home system. This class provides
 * basic functionalities to turn on and off the light. The state of the light
 * (on or off) is managed through specific commands. The light starts in an
 * 'off' state and can be toggled to 'on' or vice versa, reflecting its
 * operational status in the smart home environment.
 */
@Getter
public class Light extends Appliance {

    private boolean isOn;

    public Light(UUID serialNumber) {
        super(serialNumber);
        this.isOn = false;
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                turnOff();
                break;
            default:
                System.out.println("Command not recognized for Light.");
                break;
        }
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        this.setState(DeviceState.ON);
        System.out.println("Light turned on.");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        this.setState(DeviceState.OFF);
        System.out.println("Light turned off.");
    }

}

