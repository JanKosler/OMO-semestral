package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TV extends Appliance {

    private int currentChannel;
    private int volumeLevel;
    private boolean isOn;

    public TV(UUID serialNumber) {
        super(serialNumber);
        this.currentChannel = 1; // Default channel
        this.volumeLevel = 30;   // Default volume level
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
            case INCREASE_VOLUME:
                adjustVolume(volumeLevel + 10); // Increase volume by 10
                break;
            case DECREASE_VOLUME:
                adjustVolume(volumeLevel - 10); // Decrease volume by 10
                break;
            case NEXT_CHANNEL:
                changeChannel(currentChannel + 1);
                break;
            case PREVIOUS_CHANNEL:
                changeChannel(currentChannel - 1);
                break;
            default:
                System.out.println("Command not recognized for TV.");
                break;
        }
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        this.setState(DeviceState.ON);
        System.out.println("TV turned on.");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        this.setState(DeviceState.OFF);
        System.out.println("TV turned off.");
    }

    private void adjustVolume(int newVolume) {
        if (newVolume > 100) {
            volumeLevel = 100; // Cap the volume at 100
        } else if (newVolume < 0) {
            volumeLevel = 0; // Ensure volume doesn't go below 0
        } else {
            volumeLevel = newVolume;
        }
        System.out.println("TV volume set to " + volumeLevel);
    }


    private void changeChannel(int channel) {
        currentChannel = channel;
        System.out.println("TV channel set to " + currentChannel);
    }
}

