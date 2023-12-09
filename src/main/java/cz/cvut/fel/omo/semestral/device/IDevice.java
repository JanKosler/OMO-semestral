package cz.cvut.fel.omo.semestral.device;

public interface IDevice {
    double getPowerConsumption();
    DeviceState getState();
    int getTotalWear();
    void setState(DeviceState state);
    void turnOn();
    void turnOff();
}
