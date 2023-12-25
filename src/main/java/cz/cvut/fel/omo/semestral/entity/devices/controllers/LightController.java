package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Light;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing Light appliances in the smart home system.
 * This controller can control multiple lights and responds to both motion
 * sensor inputs and user inputs. When the motion sensor detects movement,
 * it can automatically turn on the lights. Additionally, user inputs
 * through a UserInputSensor can be used to manually toggle the lights on or off.
 */
@Getter
public class LightController extends Controller {

    private final List<Light> lights;
    private final MotionSensor motionSensor;
    private final UserInputSensor userInputSensor;

    public LightController(List<Light> lights, MotionSensor motionSensor, UserInputSensor userInputSensor) {
        this.lights = lights;
        this.motionSensor = motionSensor;
        this.userInputSensor = userInputSensor;
        if (motionSensor != null) {
            this.motionSensor.addObserver(this);
        }
        if (userInputSensor != null) {
            this.userInputSensor.addObserver(this);
        }
    }

    @Override
    public void update(IDevice device) {
        if (device instanceof MotionSensor || device instanceof UserInputSensor) {
            respondToSensor((Sensor) device);
        }
    }

    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == motionSensor) {
            // Handle motion sensor input, e.g., turn on the lights
            lights.forEach(light -> light.executeCommand(DeviceCommand.TURN_ON));
        } else if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.LIGHT_SWITCH) {
            // Handle user input for lights
            boolean isOn = (boolean) userInputSensor.getInputValue();
            lights.forEach(light -> {
                if (isOn) {
                    light.executeCommand(DeviceCommand.TURN_ON);
                } else {
                    light.executeCommand(DeviceCommand.TURN_OFF);
                }
            });
        }
    }
}

