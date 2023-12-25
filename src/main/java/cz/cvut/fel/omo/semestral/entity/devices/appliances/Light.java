package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Light extends Appliance {

    private boolean isDimmable;

    private int brightnessLevel; // Range from 0 (off) to 100 (full brightness)
    private boolean isOn;

    public Light(UUID serialNumber, boolean isDimmable) {
        super(serialNumber);
        this.isDimmable = isDimmable;
        // Lights start off and at full brightness (if they're dimmable) by default
        this.brightnessLevel = 100;
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
            case INCREASE_BRIGHTNESS:
                changeBrightness(10); // Assuming each call increases brightness by 10%
                break;
            case DECREASE_BRIGHTNESS:
                changeBrightness(-10); // Assuming each call decreases brightness by 10%
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

    // Method to change brightness; increase or decrease based on the value
    private void changeBrightness(int change) {
        if (isDimmable) {
            brightnessLevel += change;
            // Ensure the brightness level stays within the 0-100 range
            brightnessLevel = Math.max(0, Math.min(brightnessLevel, 100));
            System.out.println("Light brightness set to " + brightnessLevel + "%");
        }
    }
}

