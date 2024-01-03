package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;

public abstract class DeviceSystem {
    public void turnOn() {
        return;
    }

    public void turnOff() {
        return;
    }

    public Appliance getAppliance() {
        return null;
    }

    public UserInputSensor getUserInputSensor() {
        return null;
    }
}
