package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Gate;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.GateController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;

@Getter
public class GateControlSystem extends DeviceSystem {
    public final Gate gate;
    public final GateController controller;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.GATE_CONTROL);

    public GateControlSystem(Gate gate, GateController controller, UserInputSensor userInputSensor) {
        this.gate = gate;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
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
        gate.setIdle();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        gate.turnOff();
    }

    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        gate.onTick();
    }
}
