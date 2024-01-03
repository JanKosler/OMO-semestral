package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.GateController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

public class GateControlSystem extends DeviceSystem {
    public final Gate gate;
    public final GateController controller;
    public final UserInputSensor userInputSensor;

    public GateControlSystem(Gate gate, GateController controller, UserInputSensor userInputSensor) {
        this.gate = gate;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
    }

    @Override
    public Gate getAppliance() {
        return gate;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public void turnOn() {
        userInputSensor.turnOn();
        controller.turnOn();
        gate.turnOn();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        gate.turnOff();
    }
}
