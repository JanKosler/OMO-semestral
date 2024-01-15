package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Fridge;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.FridgeController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class FridgeSystem extends DeviceSystem {
    public final Fridge fridge;
    public final FridgeController controller;
    public final UserInputSensor userInputSensor;
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.FRIDGE_TEMPERATURE);
    private final int deviceSystemID;

    public FridgeSystem(int deviceSystemID, Fridge fridge, FridgeController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.fridge = fridge;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
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
    public double getTotalConsumption() {
        return fridge.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    @Override
    public List<IDevice> getDevices() {
        return List.of(fridge, controller, userInputSensor);
    }

    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        fridge.onTick();
    }
    @Override
    public String toString() {
        return "FridgeSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    @Override
    public void setRoom(Room room) {
        return;
    }
}
