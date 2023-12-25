package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Fridge;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

/**
 * Controller for managing a Fridge appliance within the smart home system.
 * This controller responds to user inputs specifically related to adjusting
 * the fridge's internal temperature. It observes a UserInputSensor and adjusts
 * the fridge's temperature based on user-set target values. The temperature
 * adjustment is done by incrementally increasing or decreasing the temperature
 * until the target temperature is reached.
 */
public class FridgeController extends Controller {

    private final Fridge fridge;
    private final UserInputSensor userInputSensor;

    public FridgeController(Fridge fridge, UserInputSensor userInputSensor) {
        this.fridge = fridge;
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
        if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.FRIDGE_TEMPERATURE) {
            Object input = userInputSensor.getInputValue();
            if (input instanceof Double) {
                setFridgeTemperature((Double) input);
            }
        }
    }

    private void setFridgeTemperature(double targetTemperature) {
        double currentTemperature = fridge.getInternalTemperature();

        // Calculate the difference and send appropriate commands to adjust temperature
        while (currentTemperature < targetTemperature) {
            fridge.executeCommand(DeviceCommand.INCREASE_TEMPERATURE);
            currentTemperature++;
        }
        while (currentTemperature > targetTemperature) {
            fridge.executeCommand(DeviceCommand.DECREASE_TEMPERATURE);
            currentTemperature--;
        }
    }
}

