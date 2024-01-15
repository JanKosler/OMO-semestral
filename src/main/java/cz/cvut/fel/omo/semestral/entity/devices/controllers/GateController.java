package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;

import java.util.UUID;

/**
 * Represents a controller for managing a gate within the smart home system.
 * This controller handles user commands related to the gate, such as opening or closing.
 * It monitors a UserInputSensor for gate control actions and acts on the gate accordingly,
 * effectively toggling its open or closed state based on user inputs.
 */
public class GateController extends Controller {
    /** The gate appliance that this controller manages */
    private final Gate gate;
    /** The sensor that detects user inputs for the gate */
    private final UserInputSensor userInputSensor;


    private final double powerConsumptionPerTick = 1.75; //Consumption in mWh every 10 mins.

    /**
     * Constructs a GateController with the specified gate and user input sensor.
     *
     * @param gate            The gate appliance that this controller manages.
     * @param userInputSensor The sensor that detects user inputs for the gate.
     */
    public GateController(UUID serialNumber, Gate gate, UserInputSensor userInputSensor) {
        super(serialNumber,100);
        this.gate = gate;
        this.userInputSensor = userInputSensor;
        this.userInputSensor.addObserver(this);
    }

    /**
     * Responds to updates from the connected UserInputSensor.
     * Activates gate operations based on user input commands.
     *
     * @param device The device (sensor) that has detected a change.
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
     * Handles sensor updates, specifically for user inputs related to gate control.
     * Toggles the gate's state in response to user inputs.
     *
     * @param sensor The sensor reporting the change.
     */
    @Override
    protected void respondToSensor(Sensor sensor) {
        if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.GATE_CONTROL) {
            toggleGate();
        }
    }

    /**
     * Toggles the state of the gate - opening it if closed, and closing it if open.
     */
    private void toggleGate() {
        gate.addtoActionPlan(DeviceCommand.TOGGLE_GATE);
    }
}

