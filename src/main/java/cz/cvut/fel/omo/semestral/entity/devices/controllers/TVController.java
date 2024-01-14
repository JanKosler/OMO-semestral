package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

import java.util.UUID;

/**
 * Controller responsible for managing a TV appliance in the smart home system.
 * It handles user inputs to control various TV functionalities such as power state,
 * volume level, and channel selection. This controller listens to a UserInputSensor
 * and interprets the user commands to turn the TV on or off, adjust volume, and
 * switch channels.
 */
public class TVController extends Controller {

    private final TV tv;
    private final UserInputSensor userInputSensor;
    private final double powerConsumptionPerTick = 1.75; //Consumption in mWh every 10 mins.

    /**
     * Constructs a TVController with a specific TV appliance and user input sensor.
     *
     * @param tv              The TV appliance to be controlled.
     * @param userInputSensor The sensor that captures user inputs for the TV.
     */
    public TVController(UUID serialNumber, TV tv, UserInputSensor userInputSensor) {
        super(serialNumber, 100);
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

    @Override
    public void onTick() {
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
                if ((boolean) inputValue) {
                    tv.addtoActionPlan(DeviceCommand.TURN_ON);
                } else {
                    tv.addtoActionPlan(DeviceCommand.TURN_OFF);
                }
                break;
            case TV_VOLUME:
                adjustTVVolume((Integer) inputValue);
                break;
            case TV_CHANNEL:
                changeTVChannel((Integer) inputValue);
                break;
            default:
                System.out.println("Unrecognized TV input command.");
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
        while (currentVolume != newVolume) {
            if (currentVolume < newVolume) {
                tv.addtoActionPlan(DeviceCommand.INCREASE_VOLUME);
                currentVolume++;
            } else {
                tv.addtoActionPlan(DeviceCommand.DECREASE_VOLUME);
                currentVolume--;
            }
        }
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
    }
}

