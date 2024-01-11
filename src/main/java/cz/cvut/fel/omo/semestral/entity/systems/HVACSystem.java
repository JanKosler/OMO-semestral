package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TemperatureController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;

@Getter
public class HVACSystem extends DeviceSystem {
    public final HVAC hvac;
    public final TemperatureController controller;
    public final TemperatureSensor internalSensor;
    public final TemperatureSensor externalSensor;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.HVAC_TEMPERATURE);

    public HVACSystem(HVAC hvac, TemperatureController controller, TemperatureSensor internalSensor, TemperatureSensor externalSensor, UserInputSensor userInputSensor) {
        this.hvac = hvac;
        this.controller = controller;
        this.internalSensor = internalSensor;
        this.externalSensor = externalSensor;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    @Override
    public HVAC getAppliance() {
        return hvac;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public void turnOn() {

        internalSensor.turnOn();
        externalSensor.turnOn();
        userInputSensor.turnOn();
        controller.turnOn();
        hvac.turnOn();
    }

    @Override
    public void turnOff() {

        internalSensor.turnOff();
        externalSensor.turnOff();
        userInputSensor.turnOff();
        controller.turnOff();
        hvac.turnOff();
    }

    @Override
    public void onTick() {

        internalSensor.onTick();
        externalSensor.onTick();
        userInputSensor.onTick();
        controller.onTick();
        hvac.onTick();
    }
}
