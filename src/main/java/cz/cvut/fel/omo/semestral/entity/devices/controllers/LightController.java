package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Light;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * Controller responsible for managing multiple Light appliances within the smart home system.
 * It can interact with lights based on both motion detection and user inputs.
 * When a connected MotionSensor detects movement, it can trigger the lights to turn on automatically.
 * Additionally, user commands through a UserInputSensor allow for manual control of the lights,
 * enabling users to turn them on or off as desired.
 */
@Getter
@Slf4j
public class LightController extends Controller {
    /** The list of Light appliances that this controller manages */
    private final List<Light> lights;
    /** The sensor that detects motion for the lights */
    private final MotionSensor motionSensor;
    /** The sensor that detects user inputs for the lights */
    private final UserInputSensor userInputSensor;


    private final double powerConsumptionPerTick = 1.75 / 600; //Consumption in mWh every 10 mins.
    private final int wearCapacity = 100;

    /**
     * Constructs a LightController with the specified lights, motion sensor, and user input sensor.
     *
     * @param lights          The list of Light appliances to be controlled.
     * @param motionSensor    The MotionSensor that can trigger the lights.
     * @param userInputSensor The UserInputSensor for receiving user commands related to the lights.
     */
    public LightController(UUID serialNumber,List<Light> lights, MotionSensor motionSensor, UserInputSensor userInputSensor) {
        super(serialNumber,100);
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

    /**
     * Responds to updates from connected sensors (motion or user input).
     * Determines whether to turn the lights on or off based on sensor inputs.
     *
     * @param device The sensor (either motion or user input) reporting the change.
     */
    @Override
    public void update(IDevice device) {
        if (device instanceof MotionSensor || device instanceof UserInputSensor) {
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
     * Handles sensor updates for motion detection and user inputs.
     * Automatically turns on the lights upon motion detection, or toggles them
     * based on user commands.
     *
     * @param sensor The sensor reporting the change.
     */
    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == motionSensor) {
            if(motionSensor.isMotionDetected()) {
                turnOnAllLights();
            } else {
                turnOffAllLights();
            }
        } else if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.LIGHT_SWITCH) {
            // Handle user input for lights
            boolean isOn = (boolean) userInputSensor.getInputValue();
            if(isOn) {
                turnOnAllLights();
            } else {
                turnOffAllLights();
            }
        }
    }

    private void turnOnAllLights() {
        lights.forEach(light -> light.addtoActionPlan(DeviceCommand.TURN_ON));
        log.info("Controller: Lights turned on.");
    }

    private void turnOffAllLights() {
        lights.forEach(light -> light.addtoActionPlan(DeviceCommand.TURN_OFF));
        log.info("Controller: Lights turned off.");
    }
}

