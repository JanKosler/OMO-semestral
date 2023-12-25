package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.common.enums.UserInputType;

/**
 * Controller dedicated to managing a Gate appliance within the smart home system.
 * This controller specifically responds to user inputs for controlling the gate,
 * such as opening and closing it. It observes a UserInputSensor and reacts to gate
 * control commands by toggling the state of the gate.
 */
public class GateController extends Controller {

    private final Gate gate;
    private final UserInputSensor userInputSensor;

    public GateController(Gate gate, UserInputSensor userInputSensor) {
        this.gate = gate;
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
        if (sensor == userInputSensor && userInputSensor.getInputType() == UserInputType.GATE_CONTROL) {
            toggleGate();
        }
    }

    private void toggleGate() {
        gate.executeCommand(DeviceCommand.TOGGLE_GATE);
    }
}

