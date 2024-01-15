package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceCommand;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.manual.Manual;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Abstract base class for appliances in the smart home system. This class
 * serves as a foundation for various types of home appliances, providing
 * common attributes such as serial number, state, wear, and power consumption.
 * Each specific appliance type extends this class and implements its own
 * behavior through the executeCommand method, allowing for dynamic control
 * of appliance actions such as turning on or off and adjusting settings.
 */
@Getter
@Setter
@Slf4j
public abstract class Appliance implements IDevice, IDeviceCommand, Tickable {
    /** The unique serial number of the appliance */
    private UUID serialNumber;
    /** The current state of the appliance */
    protected DeviceState state;
    /** The total wear of the appliance */
    private int totalWear;
    /** The total power consumption of the appliance */
    private double totalPowerConsumption;
    /** The maximum wear capacity of the appliance */
    private int wearCapacity;
    /** List of observers that are notified when the appliance malfunctions */
    private List<DeviceMalfunctionObserver> malfunctionObservers = new ArrayList<>();

    protected Queue<DeviceCommand> actionPlan;

    /**
     * Constructs a new Appliance with the specified serial number and wear capacity.
     * The appliance is initialized with a default state of OFF, total wear of 0,
     * and total power consumption of 0.
     *
     * @param serialNumber The unique identifier for this appliance.
     * @param wearCapacity The maximum wear capacity of this appliance.
     */
    public Appliance(UUID serialNumber, int wearCapacity) {
        this.serialNumber = serialNumber;
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.totalPowerConsumption = 0;
        this.wearCapacity = wearCapacity;
        this.actionPlan = new java.util.LinkedList<>();
    }

    /**
     * Constructs a new Appliance with the specified serial number, state, total wear,
     * total power consumption, and wear capacity.
     *
     * @param serialNumber         The unique identifier for this appliance.
     * @param state                The current state of this appliance.
     * @param totalWear            The total wear of this appliance.
     * @param totalPowerConsumption The total power consumption of this appliance.
     * @param wearCapacity         The maximum wear capacity of this appliance.
     */
    public Appliance(UUID serialNumber, DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.serialNumber = serialNumber;
        this.state = state;
        this.totalWear = totalWear;
        this.totalPowerConsumption = totalPowerConsumption;
        this.wearCapacity = wearCapacity;
        this.actionPlan = new java.util.LinkedList<>();
    }

    /**
     * Executes the specified command on this appliance.
     * This method should be implemented by subclasses to define specific behaviors.
     *
     * @param command The command to be executed.
     */
    public abstract void executeCommand(DeviceCommand command);

    /**
     * Sets the state of the appliance to the specified state.
     * If the new state is different from the current state, the appliance
     * state is updated and the appliance is notified of the change.
     *
     * @param state The new state of the appliance.
     */
    public void setState(DeviceState state) {
        if (this.state != state) {
            this.state = state;
        }
    }

    /**
     * Turns the appliance on.
     * This method changes the state of the appliance to ON.
     */
    public void turnOn() {
        this.state = DeviceState.ON;
    }

    /**
     * Turns the appliance off.
     * This method changes the state of the appliance to OFF.
     */
    public void turnOff() {
        this.state = DeviceState.OFF;
    }

    /**
     * This method changes the state of the appliance to IDLE.
     */
    public void setIdle() {this.state = DeviceState.IDLE;}

    /**
     * Updates the total power consumption of the device.
     * This method increments the existing power consumption value by the specified amount.
     *
     * @param powerConsumption the amount of power to add to the total consumption.
     */
    public void updatePowerConsumption(double powerConsumption){
        this.totalPowerConsumption += powerConsumption;
    };

    /**
     * Updates the wear of the device.
     * This method increments the total wear of the device by the specified amount.
     *
     * @param wear the amount of wear to add to the total wear.
     */
    public void updateWear(int wear){
        this.totalWear += wear;
    };

    /**
     * Adds a malfunction observer to this device.
     * The observer will be notified when the device malfunctions.
     *
     * @param observer the observer to be added.
     */
    @Override
    public void addMalfunctionObserver(DeviceMalfunctionObserver observer) {
        malfunctionObservers.add(observer);
    }

    /**
     * Notifies all registered malfunction observers about a malfunction.
     * This method is called when the device state changes to MALFUNCTION.
     */
    @Override
    public void notifyMalfunctionObservers() {
        for (DeviceMalfunctionObserver observer : malfunctionObservers) {
            observer.onDeviceMalfunction(this);
        }
    }

    /**
     * Checks if the device is broken based on its wear.
     * If the total wear exceeds the wear capacity, the device state is set to MALFUNCTION.
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
     * Abstract method to be implemented by subclasses to define actions on each tick.
     */
    public abstract void onTick();

    /**
     * Adds a device command to the action plan queue.
     * The command will be executed when performAllActions() is called.
     *
     * @param command the device command to be added to the action plan.
     */
    public void addtoActionPlan(DeviceCommand command) {
        actionPlan.add(command);
    }

    /**
     * Executes all actions in the action plan.
     * This method processes and executes each command in the action plan queue.
     */
    public void performAllActions() {
        while (!actionPlan.isEmpty()) {
            DeviceCommand command = actionPlan.poll();
            executeCommand(command);
        }
    }

    /**
     * Returns the serial number of the device.
     *
     * @return the UUID representing the serial number of the device.
     */
    @Override
    public UUID getSerialNumber() {
        return serialNumber;
    }

    /**
     * Repairs the device using a manual.
     * This method resets the wear of the device and sets its state to ON.
     *
     * @param manual the manual used for repairing the device.
     */
    @Override
    public void repair(Manual manual){
        this.setState(DeviceState.ON);
        this.totalWear = 0;
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired with manual.");
    }

    /**
     * Repairs the device without a manual.
     * This method partially resets the wear of the device and sets its state to ON.
     */
    @Override
    public void repair(){
        this.setState(DeviceState.ON);
        this.totalWear = new Random().nextInt(wearCapacity/2);
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired without manual.");
    }



}
