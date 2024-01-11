package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for sensors in the smart home simulation.
 * This class implements the {@link IDevice} interface, providing common functionalities for different types of sensors.
 */
@Getter
@Setter
public abstract class Sensor implements IDevice, Tickable {

    private DeviceState state;
    private int totalWear;
    private double totalPowerConsumption;
    private int wearCapacity;
    private List<DeviceMalfunctionObserver> malfunctionObservers = new ArrayList<>();

    private final List<IDeviceObserver> observers = new ArrayList<>();

    /**
     * Constructs a Sensor with default settings.
     */
    public Sensor(int wearCapacity) {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.totalPowerConsumption = 0;
        this.wearCapacity = wearCapacity;
    }

    /**
     * Constructs a Sensor with specified state, total wear, and power consumption.
     *
     * @param state The initial state of the sensor.
     * @param totalWear The initial total wear of the sensor.
     * @param totalPowerConsumption The initial power consumption of the sensor.
     */
    public Sensor(DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.state = state;
        this.totalWear = totalWear;
        this.totalPowerConsumption = totalPowerConsumption;
        this.wearCapacity = wearCapacity;
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

    public void updatePowerConsumption(double powerConsumption){
        this.totalPowerConsumption += powerConsumption;
    };
    public void updateWear(int wear){
        this.totalWear += wear;
    };

    @Override
    public void addMalfunctionObserver(DeviceMalfunctionObserver observer) {
        malfunctionObservers.add(observer);
    }

    @Override
    public void notifyMalfunctionObservers() {
        for (DeviceMalfunctionObserver observer : malfunctionObservers) {
            observer.onDeviceMalfunction(this);
        }
    }

    public void checkIfBroken() {
        if (this.getState() != DeviceState.MALFUNCTION) {
            if (this.totalWear >= wearCapacity) {
                this.setState(DeviceState.MALFUNCTION);
                notifyMalfunctionObservers();
            }
        }
    }
    public abstract void onTick();
}
