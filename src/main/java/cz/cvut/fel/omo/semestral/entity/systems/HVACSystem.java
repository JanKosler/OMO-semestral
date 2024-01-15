package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.HVAC;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.Controller;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TemperatureController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.TemperatureSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
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
    private final int deviceSystemID;

    public HVACSystem(int deviceSystemID,HVAC hvac, TemperatureController controller, TemperatureSensor internalSensor, TemperatureSensor externalSensor, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
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
    public Controller getController() {
        return controller;
    }

    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    @Override
    public double getTotalConsumption() {
        return hvac.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption() + internalSensor.getTotalPowerConsumption() + externalSensor.getTotalPowerConsumption();
    }

    @Override
    public List<IDevice> getDevices() {
        return List.of(hvac, controller, userInputSensor, internalSensor, externalSensor);
    }

    @Override
    public void onTick() {
        internalSensor.onTick();
        externalSensor.onTick();
        userInputSensor.onTick();
        controller.onTick();
        hvac.onTick();
    }

    @Override
    public String toString() {
        return "HVACSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    @Override
    public void setRoom(Room room) {
        return;
    }
}
