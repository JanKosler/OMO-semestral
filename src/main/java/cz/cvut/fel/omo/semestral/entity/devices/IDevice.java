package cz.cvut.fel.omo.semestral.entity.devices;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;

public interface IDevice {
    double getPowerConsumption();
    DeviceState getState();
    int getTotalWear();
    void setState(DeviceState state);
    void turnOn();
    void turnOff();
}
