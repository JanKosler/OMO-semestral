package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import lombok.Getter;

@Getter
public abstract class Controller implements IDevice, IDeviceObserver {

    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    public Controller() {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    public Controller(DeviceState state, int totalWear, double powerConsumption) {
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
    }

    protected abstract void respondToSensor(Sensor sensor);

    @Override
    public void update(IDevice device) {
        if (device instanceof Sensor) {
            respondToSensor((Sensor) device);
        }
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public void turnOn() {
        this.state = DeviceState.ON;
    }

    public void turnOff() {
        this.state = DeviceState.OFF;
    }

    public void setTotalWear(int totalWear) {
        this.totalWear = totalWear;
    }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public void increaseTotalWear(int wear) {
        this.totalWear += wear;
    }

    public void increasePowerConsumption(double powerConsumption) {
        this.powerConsumption += powerConsumption;
    }

    public void decreasePowerConsumption(double powerConsumption) {
        this.powerConsumption -= powerConsumption;
    }

    public void decreaseTotalWear(int wear) {
        this.totalWear -= wear;
    }

}
