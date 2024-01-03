package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import lombok.Getter;

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
public abstract class Controller implements IDevice, IDeviceObserver {

    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    /**
     * Constructs a Controller with default settings.
     */
    public Controller() {
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    /**
     * Constructs a Controller with specified initial state, total wear, and power consumption.
     *
     * @param state            The initial state of the controller.
     * @param totalWear        The initial total wear of the controller.
     * @param powerConsumption The initial power consumption of the controller.
     */
    public Controller(DeviceState state, int totalWear, double powerConsumption) {
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
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
