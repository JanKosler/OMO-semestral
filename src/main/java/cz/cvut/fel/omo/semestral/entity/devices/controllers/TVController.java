package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.actions.ControllerRecord;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.UUID;

/**
 * Controller responsible for managing a TV appliance in the smart home system.
 * It handles user inputs to control various TV functionalities such as power state,
 * volume level, and channel selection. This controller listens to a UserInputSensor
 * and interprets the user commands to turn the TV on or off, adjust volume, and
 * switch channels.
 */
@Slf4j
public class TVController extends Controller {
    /** The TV appliance that this controller manages */
    private final TV tv;
    /** The sensor that detects user inputs for the TV */
    private final UserInputSensor userInputSensor;
    private final double powerConsumptionPerTick = 1.75 / 600.00; //Consumption in mWh every 10 mins.

    /**
     * Constructs a TVController with a specific TV appliance and user input sensor.
     *
     * @param tv              The TV appliance to be controlled.
     * @param userInputSensor The sensor that captures user inputs for the TV.
     */
    public TVController(UUID serialNumber, TV tv, UserInputSensor userInputSensor) {
        super(serialNumber, new Random().nextInt(250)+100);
        this.tv = tv;
        this.userInputSensor = userInputSensor;
        this.userInputSensor.addObserver(this);
    }

    /**
     * Responds to updates from the connected UserInputSensor.
     * Processes user commands to control the TV's functionalities.
     *
     * @param device The device (sensor) that has detected a change in user input.
     */
    @Override
    public void update(IDevice device) {
        if (device instanceof UserInputSensor) {
            respondToSensor((Sensor) device);
        }
    }

    /**
     * Performs actions during each tick.
     * Updates wear, power consumption, and checks for malfunctions.
     */
    @Override
    public void onTick() {
        setTickCounter(getTickCounter() + 1);
        if (this.getState() == DeviceState.ON) {
            updateWear(1);
            updatePowerConsumption(powerConsumptionPerTick);
            checkIfBroken();
        }
    }

    /**
     * Processes the inputs detected by the UserInputSensor and executes corresponding commands on the TV.
     * This method handles inputs for turning the TV on/off, changing volume, and selecting channels.
     */
    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == userInputSensor) {
            processTVInput();
        }
    }

    /**
     * Processes the specific user input received for the TV.
     * Executes the appropriate command on the TV based on the user input type and value.
     */
    private void processTVInput() {
        UserInputType inputType = userInputSensor.getInputType();
        Object inputValue = userInputSensor.getInputValue();

        switch (inputType) {
            case TV_POWER:
                if (tv.getState() == DeviceState.IDLE) {
                    tv.addtoActionPlan(DeviceCommand.TURN_ON);
                    records.add(new ControllerRecord(this.getTickCounter(),this, "TV has been turned on."));
                } else {
                    tv.addtoActionPlan(DeviceCommand.TURN_OFF);
                    records.add(new ControllerRecord(this.getTickCounter(),this, "TV has been turned off."));
                }
                break;
            case TV_VOLUME:
                adjustTVVolume((Integer) inputValue);
                break;
            case TV_CHANNEL:
                changeTVChannel((Integer) inputValue);
                break;
            default:
                log.warn("Controller: Invalid user input type for TV: " + inputType);
                break;
        }
    }

    /**
     * Adjusts the TV's volume to a specified level.
     * The method incrementally changes the volume until it reaches the desired level.
     *
     * @param newVolume The target volume level.
     */
    private void adjustTVVolume(int newVolume) {
        // Assuming the newVolume is the desired volume level
        int currentVolume = tv.getVolumeLevel();
        if(newVolume> 10){newVolume = 10;}
        else if(newVolume < 0){newVolume = 0;}

        while (currentVolume != newVolume) {
            if (currentVolume < newVolume) {
                tv.addtoActionPlan(DeviceCommand.INCREASE_VOLUME);
                currentVolume++;
            } else {
                tv.addtoActionPlan(DeviceCommand.DECREASE_VOLUME);
                currentVolume--;
            }
        }
        log.info("Controller: TV volume set to " + newVolume);
        records.add(new ControllerRecord(this.getTickCounter(),this, "TV volume set to " + newVolume));
    }

    /**
     * Changes the TV channel to a specified channel number.
     * The method switches channels one at a time until it reaches the target channel.
     *
     * @param newChannel The target channel number.
     */
    private void changeTVChannel(int newChannel) {
        // Assuming the newChannel is the desired channel
        int currentChannel = tv.getCurrentChannel();
        while (currentChannel != newChannel) {
            if (currentChannel < newChannel) {
                tv.addtoActionPlan(DeviceCommand.NEXT_CHANNEL);
                currentChannel++;
            } else {
                tv.addtoActionPlan(DeviceCommand.PREVIOUS_CHANNEL);
                currentChannel--;
            }
        }
        log.info("Controller: TV channel set to " + newChannel);
        records.add(new ControllerRecord(this.getTickCounter(),this, "TV channel set to " + newChannel));
    }
}

