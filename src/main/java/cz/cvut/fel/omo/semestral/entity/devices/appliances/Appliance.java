package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceCommand;
import cz.cvut.fel.omo.semestral.entity.devices.DeviceMalfunctionObserver;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

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

    public Appliance(UUID serialNumber, int wearCapacity) {
        this.serialNumber = serialNumber;
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.totalPowerConsumption = 0;
        this.wearCapacity = wearCapacity;
        this.actionPlan = new java.util.LinkedList<>();
    }

    public Appliance(UUID serialNumber, DeviceState state, int totalWear, double totalPowerConsumption, int wearCapacity) {
        this.serialNumber = serialNumber;
        this.state = state;
        this.totalWear = totalWear;
        this.totalPowerConsumption = totalPowerConsumption;
        this.wearCapacity = wearCapacity;
        this.actionPlan = new java.util.LinkedList<>();
    }

    public abstract void executeCommand(DeviceCommand command);

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

    public void setIdle() {this.state = DeviceState.IDLE;}

    public void updatePowerConsumption(double powerConsumption){
        if(this instanceof Fridge){
            log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has consumed " + powerConsumption + " kWh.");
            log.info("Total consumption: " + this.totalPowerConsumption + " kWh.");
        }
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
                notifyMalfunctionObservers();

            }
        }
    }

    public abstract void onTick();

    public void addtoActionPlan(DeviceCommand command) {
        actionPlan.add(command);
    }

    public void performAllActions() {
        while (!actionPlan.isEmpty()) {
            DeviceCommand command = actionPlan.poll();
            executeCommand(command);
        }
    }

    @Override
    public UUID getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void repair(){
        this.setState(DeviceState.ON);
        this.totalWear = 0;
        log.info(this.getClass().getSimpleName() + " " + this.getSerialNumber() + " has been repaired.");
    }

}
