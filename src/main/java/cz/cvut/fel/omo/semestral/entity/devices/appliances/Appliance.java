package cz.cvut.fel.omo.semestral.entity.devices.appliances;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.IDeviceCommand;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Appliance implements IDevice, IDeviceCommand {

    private UUID serialNumber;
    private DeviceState state;
    private int totalWear;
    private double powerConsumption;

    public Appliance(UUID serialNumber) {
        this.serialNumber = serialNumber;
        this.state = DeviceState.OFF;
        this.totalWear = 0;
        this.powerConsumption = 0;
    }

    public Appliance(UUID serialNumber, DeviceState state, int totalWear, double powerConsumption) {
        this.serialNumber = serialNumber;
        this.state = state;
        this.totalWear = totalWear;
        this.powerConsumption = powerConsumption;
    }

    public abstract void executeCommand(DeviceCommand command);

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
