package cz.cvut.fel.omo.semestral.entity.devices.sensors;

import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceObserver;
import cz.cvut.fel.omo.semestral.manual.Manual;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Abstract base class for sensors in the smart home simulation.
 * This class implements the {@link IDevice} interface, providing common functionalities for different types of sensors.
 */
@Getter
@Setter
@Slf4j
public abstract class Sensor implements IDevice, Tickable {
    /** The unique serial number of the sensor */
    private UUID serialNumber;
    /** The current state of the sensor */
    private DeviceState state;
    /** The total wear of the sensor */
    private int totalWear;
    /** The total power consumption of the sensor */
    private double totalPowerConsumption;
    /** The maximum wear capacity of the sensor */
    private int wearCapacity;
    /** List of observers that are notified when the sensor malfunctions */
    private List<DeviceMalfunctionObserver> malfunctionObservers = new ArrayList<>();
    /** List of observers that are notified when the sensor state changes */
    private final List<IDeviceObserver> observers = new ArrayList<>();

    /**
     * Constructs a Sensor with default settings.
     */
    public Sensor(UUID serialNumber, int wearCapacity) {
        this.serialNumber = serialNumber;
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
    public Sensor(UUID serialNumber, DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.serialNumber = serialNumber;
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

    /**
     * Updates the power consumption of the sensor.
     *
     * @param powerConsumption The amount of power consumption to update.
     */
    public void updatePowerConsumption(double powerConsumption){
        this.totalPowerConsumption += powerConsumption;
    };

    /**
     * Updates the total wear of the sensor.
     *
     * @param wear The amount of wear to update.
     */
    public void updateWear(int wear){
        this.totalWear += wear;
    };

    /**
     * Adds a malfunction observer to the sensor.
     *
     * @param observer The observer to be added.
     */
    @Override
    public void addMalfunctionObserver(DeviceMalfunctionObserver observer) {
        malfunctionObservers.add(observer);
    }

    /**
     * Notifies all malfunction observers about the sensor malfunctioning.
     */
    @Override
    public void notifyMalfunctionObservers() {
        for (DeviceMalfunctionObserver observer : malfunctionObservers) {
            observer.onDeviceMalfunction(this);
        }
    }

    /**
     * Checks if the sensor has broken down.
     * If the sensor has broken down, its state is set to MALFUNCTION and all observers are notified.
     */
    public void checkIfBroken() {
        if (this.getState() != DeviceState.MALFUNCTION) {
            if (this.totalWear >= wearCapacity) {
                this.setState(DeviceState.MALFUNCTION);
                log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has broken.");
                notifyMalfunctionObservers();
            }
        }
    }

    /**
     * Performs actions during each tick.
     * Subclasses should implement this method for specific sensor behaviors.
     */
    public abstract void onTick();

    /**
     * Gets the serial number of the sensor.
     *
     * @return The serial number of the sensor.
     */
    @Override
    public UUID getSerialNumber() {
        return serialNumber;
    }

    /**
     * Repairs the sensor with a manual.
     *
     * @param manual The manual to use for repair.
     */
    @Override
    public void repair(Manual manual){
        this.setState(DeviceState.ON);
        this.totalWear = 0;
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired with manual.");
    }

    /**
     * Repairs the sensor without a manual.
     */
    @Override
    public void repair(){
        this.setState(DeviceState.ON);
        this.totalWear = new Random().nextInt(wearCapacity/2);
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired without manual.");
    }
}
