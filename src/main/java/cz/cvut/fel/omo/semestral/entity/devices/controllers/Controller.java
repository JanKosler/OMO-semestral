package cz.cvut.fel.omo.semestral.entity.devices.controllers;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.actions.ControllerRecord;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.Sensor;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
@Slf4j
public abstract class Controller implements IDevice, IDeviceObserver, Tickable {
    /** The unique serial number of the controller */
    private UUID serialNumber;
    /** The current state of the controller */
    private DeviceState state;
    /** The total wear of the controller */
    private int totalWear;
    /** The total power consumption of the controller */
    private double totalPowerConsumption;
    /** The maximum wear capacity of the controller */
    private int wearCapacity;
    /** List of observers that are notified when the controller malfunctions */
    private List<DeviceMalfunctionObserver> malfunctionObservers = new ArrayList<>();

    protected List<ControllerRecord> records = new ArrayList<>();
    private int tickCounter = 0;

    /**
     * Constructs a Controller with default settings.
     */
    public Controller(UUID serialNumber, int wearCapacity) {
        this.serialNumber = serialNumber;
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
    public Controller(UUID serialNumber, DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.serialNumber = serialNumber;
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
                log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has broken.");
                this.records.add(new ControllerRecord(this.getTickCounter(),this, "has broken."));
                notifyMalfunctionObservers();

            }
        }
    }

    public abstract void onTick();


    @Override
    public UUID getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void repair(){
        this.setState(DeviceState.ON);
        this.totalWear = 0;
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired.");
        this.records.add(new ControllerRecord(this.getTickCounter(),this, "has been repaired."));
    }
}
