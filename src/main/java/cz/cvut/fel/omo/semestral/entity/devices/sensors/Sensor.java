package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for sensors in the smart home simulation.
 * This class implements the {@link IDevice} interface, providing common functionalities for different types of sensors.
 */
@Getter
public abstract class Sensor implements IDevice {

    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    private final List<IDeviceObserver> observers = new ArrayList<>();

    /**
     * Constructs a Sensor with default settings.
     */
    public Sensor() {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    /**
     * Constructs a Sensor with specified state, total wear, and power consumption.
     *
     * @param state The initial state of the sensor.
     * @param totalWear The initial total wear of the sensor.
     * @param powerConsumption The initial power consumption of the sensor.
     */
    public Sensor(DeviceState state, int totalWear, double powerConsumption) {
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
    }

    /**
     * Adds an observer to the sensor.
     *
     * @param observer The observer to be added.
     */
    public void addObserver(IDeviceObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the sensor.
     *
     * @param observer The observer to be removed.
     */
    public void removeObserver(IDeviceObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers about the state change of the sensor.
     */
    public void notifyObservers() {
        for (IDeviceObserver observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Sets the state of the sensor and notifies observers if there is a change.
     *
     * @param state The new state to set for the sensor.
     */
    public void setState(DeviceState state) {
        if (this.state != state) {
            this.state = state;
            notifyObservers();
        }
    }

    /**
     * Turns the sensor on.
     */
    public void turnOn() {
        this.state = DeviceState.ON;
    }

    /**
     * Turns the sensor off.
     */
    public void turnOff() {
        this.state = DeviceState.OFF;
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
