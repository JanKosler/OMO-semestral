package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract base class for controllers in the smart home system.
 * Controllers are responsible for interpreting sensor data and managing the behavior of connected appliances.
 * Each specific controller implementation focuses on a particular aspect of the home environment,
 * such as temperature control, lighting, or security, reacting to changes detected by sensors and
 * making decisions to maintain desired environmental conditions.
 * The Controller class implements both IDevice and IDeviceObserver interfaces,
 * indicating its dual role in responding to sensor updates and managing device states.
 */
@Getter
@Setter
public abstract class Controller implements IDevice, IDeviceObserver, Tickable {

    private DeviceState state;
    private int totalWear;
    private double totalPowerConsumption;
    private int wearCapacity;
    private List<DeviceMalfunctionObserver> malfunctionObservers = new ArrayList<>();

    /**
     * Constructs a Controller with default settings.
     */
    public Controller(int wearCapacity) {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.totalPowerConsumption = 0;
        this.wearCapacity = wearCapacity;
    }

    /**
     * Constructs a Controller with specified initial state, total wear, and power consumption.
     *
     * @param state            The initial state of the controller.
     * @param totalWear        The initial total wear of the controller.
     * @param totalPowerConsumption The initial power consumption of the controller.
     */
    public Controller(DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.state = state;
        this.totalWear = totalWear;
        this.totalPowerConsumption = totalPowerConsumption;
        this.wearCapacity = wearCapacity;
    }

    /**
     * Responds to updates from sensors. This method is abstract and should be implemented by subclasses
     * to define specific responses to sensor changes.
     *
     * @param sensor The sensor that has detected a change.
     */
    protected abstract void respondToSensor(Sensor sensor);

    /**
     * Updates the controller based on a change in a connected device's state.
     * Implements the IDeviceObserver interface's update method.
     *
     * @param device The device that has undergone a change.
     */
    @Override
    public void update(IDevice device) {
        if (device instanceof Sensor) {
            respondToSensor((Sensor) device);
        }
    }

    public void setState(DeviceState state) {
        if (this.state != state) {
            this.state = state;
        }
    }

    public void turnOn() {
        this.state = DeviceState.ON;
    }

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
