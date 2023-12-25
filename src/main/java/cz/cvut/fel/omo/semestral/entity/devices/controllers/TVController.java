package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

/**
 * Controls a TV appliance based on user inputs. Manages power state, volume adjustments,
 * and channel changes. Responds to user inputs for turning the TV on/off, modifying volume,
 * and navigating channels.
 */
public class TVController extends Controller {

    private final TV tv;
    private final UserInputSensor userInputSensor;

    public TVController(TV tv, UserInputSensor userInputSensor) {
        this.tv = tv;
        this.userInputSensor = userInputSensor;
        this.userInputSensor.addObserver(this);
    }

    @Override
    public void update(IDevice device) {
        if (device instanceof UserInputSensor) {
            respondToSensor((Sensor) device);
        }
    }

    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == userInputSensor) {
            processTVInput();
        }
    }

    private void processTVInput() {
        UserInputType inputType = userInputSensor.getInputType();
        Object inputValue = userInputSensor.getInputValue();

        switch (inputType) {
            case TV_POWER:
                if ((boolean) inputValue) {
                    tv.executeCommand(DeviceCommand.TURN_ON);
                } else {
                    tv.executeCommand(DeviceCommand.TURN_OFF);
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

    private void adjustTVVolume(int newVolume) {
        // Assuming the newVolume is the desired volume level
        int currentVolume = tv.getVolumeLevel();
        while (currentVolume != newVolume) {
            if (currentVolume < newVolume) {
                tv.executeCommand(DeviceCommand.INCREASE_VOLUME);
                currentVolume++;
            } else {
                tv.executeCommand(DeviceCommand.DECREASE_VOLUME);
                currentVolume--;
            }
        }
    }

    private void changeTVChannel(int newChannel) {
        // Assuming the newChannel is the desired channel
        int currentChannel = tv.getCurrentChannel();
        while (currentChannel != newChannel) {
            if (currentChannel < newChannel) {
                tv.executeCommand(DeviceCommand.NEXT_CHANNEL);
                currentChannel++;
            } else {
                tv.executeCommand(DeviceCommand.PREVIOUS_CHANNEL);
                currentChannel--;
            }
        }
    }
}

