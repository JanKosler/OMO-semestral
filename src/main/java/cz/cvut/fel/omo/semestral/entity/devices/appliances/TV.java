package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class TV extends Appliance {
    /** The current channel of the TV */
    private int currentChannel;
    /** The current volume level of the TV */
    private int volumeLevel;
    /** The power consumption of the TV per tick when turned off */
    private final double powerConsumptionPerTick_IDLE = (double) 4 / 600;
    /** The power consumption of the TV per tick when turned on */
    private final double powerConsumptionPerTick_ON = (double) 35 / 600;

    /**
     * Constructs a new TV instance with the specified serial number.
     *
     * @param serialNumber The unique identifier for the TV.
     */
    public TV(UUID serialNumber) {
        super(serialNumber, 500);
        this.currentChannel = 1; // Default channel
        this.volumeLevel = 5;   // Default volume level
    }

    /**
     * Executes a command on the TV, such as turning it on or off, adjusting volume,
     * or changing the channel.
     *
     * @param command The command to execute on the TV.
     */
    @Override
    public void executeCommand(DeviceCommand command) {
        switch (command) {
            case TURN_ON:
                turnOn();
                log.info("TV: Turned on.");
                break;
            case TURN_OFF:
                setIdle();
                log.info("TV: Turned set to Idle.");
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
                log.warn("TV: Invalid command.");
                break;
        }
    }

    /**
     * Performs actions on the TV during each tick.
     */
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

    /**
     * Adjusts the volume of the TV to the specified new volume level.
     *
     * @param newVolume The new volume level to set for the TV.
     */
    public void adjustVolume(int newVolume) {
        if (newVolume > 10) {
            volumeLevel = 10; // Cap the volume at 100
        } else if (newVolume < 0) {
            volumeLevel = 0; // Ensure volume doesn't go below 0
        } else {
            volumeLevel = newVolume;
        }

    }

    /**
     * Changes the channel of the TV to the specified channel.
     *
     * @param channel The new channel to set for the TV.
     */
    private void changeChannel(int channel) {
        currentChannel = channel;
    }
}

