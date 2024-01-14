package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.UUID;

/**
 * Represents a Television (TV) in the smart home system. This class provides
 * functionalities for turning the TV on and off, adjusting volume, and changing
 * channels. The state of the TV (on or off), current volume level, and channel
 * are managed through specific commands. The TV starts with a default channel and
 * volume level, and its operational status is indicated by its state within the
 * smart home environment.
 */
@Getter
public class TV extends Appliance {

    private int currentChannel;
    private int volumeLevel;
    private final double powerConsumptionPerTick_IDLE = 4;
    private final double powerConsumptionPerTick_ON = 35;

    public TV(UUID serialNumber) {
        super(serialNumber, 100);
        this.currentChannel = 1; // Default channel
        this.volumeLevel = 5;   // Default volume level
    }

    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                break;
            case TURN_OFF:
                setIdle();
                break;
            case INCREASE_VOLUME:
                adjustVolume(volumeLevel + 1);
                break;
            case DECREASE_VOLUME:
                adjustVolume(volumeLevel - 1);
                break;
            case NEXT_CHANNEL:
                changeChannel(currentChannel + 1);
                break;
            case PREVIOUS_CHANNEL:
                changeChannel(currentChannel - 1);
                break;
            default:
                System.out.println("TV: Command not recognized for TV.");
                break;
        }
    }

    @Override
    public void onTick() {
        DeviceState currentState = this.getState();
        if(currentState != DeviceState.OFF && currentState!= DeviceState.MALFUNCTION) {
            performAllActions();
            if (currentState == DeviceState.IDLE) {
                updatePowerConsumption(powerConsumptionPerTick_IDLE);
                updateWear(1);
            } else if (currentState == DeviceState.ON) {
                updatePowerConsumption(powerConsumptionPerTick_ON);
                updateWear(10);
            }
            checkIfBroken();
        }
    }

    public void adjustVolume(int newVolume) {
        if (newVolume > 10) {
            volumeLevel = 10; // Cap the volume at 100
        } else if (newVolume < 0) {
            volumeLevel = 0; // Ensure volume doesn't go below 0
        } else {
            volumeLevel = newVolume;
        }

    }


    private void changeChannel(int channel) {
        currentChannel = channel;
    }
}

