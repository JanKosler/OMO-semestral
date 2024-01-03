package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Fridge;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.FridgeController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

public class FridgeSystem extends DeviceSystem {
    public final Fridge fridge;
    public final FridgeController controller;
    public final UserInputSensor userInputSensor;

    public FridgeSystem(Fridge fridge, FridgeController controller, UserInputSensor userInputSensor) {
        this.fridge = fridge;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
    }

    @Override
    public Fridge getAppliance() {
        return fridge;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public void turnOn() {
        userInputSensor.turnOn();
        controller.turnOn();
        fridge.turnOn();
    }

    @Override
    public void turnOff() {
        userInputSensor.turnOff();
        controller.turnOff();
        fridge.turnOff();
    }
}
