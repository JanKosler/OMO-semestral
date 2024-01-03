package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TVController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import lombok.Getter;

import java.util.List;

@Getter
public class TVSystem extends DeviceSystem {
    public final TV tv;
    public final TVController controller;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.TV_VOLUME, UserInputType.TV_CHANNEL);

    public TVSystem(TV tv, TVController controller, UserInputSensor userInputSensor) {
        this.tv = tv;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
    }

    @Override
    public TV getAppliance() {
        return tv;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public void turnOn() {
        userInputSensor.turnOn();
        controller.turnOn();
        tv.turnOn();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        tv.turnOff();
    }
}
