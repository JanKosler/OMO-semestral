package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for various sensors in the smart home system, capable of monitoring
 * specific conditions and notifying observers about changes.
 */
@Getter
public abstract class Sensor implements IDevice {

    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    private final List<IDeviceObserver> observers = new ArrayList<>();

    public Sensor() {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    public Sensor(DeviceState state, int totalWear, double powerConsumption) {
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
    }

    public void addObserver(IDeviceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IDeviceObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (IDeviceObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setState(DeviceState state) {
        if (this.state != state) {
            this.state = state;
            notifyObservers();
        }
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
