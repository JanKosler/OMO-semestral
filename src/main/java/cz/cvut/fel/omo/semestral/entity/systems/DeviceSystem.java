package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
import cz.cvut.fel.omo.semestral.common.enums.DeviceState;
import lombok.Getter;

import java.util.List;

/**
 * Abstract class representing a generic device system in the smart home simulation.
 * This class serves as a base for specific types of device systems, providing common functionalities and properties.
 * The purpose of device system is to aggregate devices and sensors that are related to each other.
 */
@Getter
public abstract class DeviceSystem implements Tickable {


    private List<UserInputType> allowedUserInputTypes;
    private int deviceSystemID;


    /**
     * Turns on the devices associated with this system.
     * This method should be overridden to provide specific turn-on behavior.
     */
    public void turnOn() {
        for(IDevice device : getDevices()) {
            device.turnOn();
        }
    }

    /**
     * Turns off the devices associated with this system.
     * This method should be overridden to provide specific turn-off behavior.
     */
    public void turnOff() {
        for(IDevice device : getDevices()) {
            device.turnOff();
        }
    }

    /**
     * Retrieves the appliance associated with this device system.
     *
     * @return An instance of Appliance associated with this system.
     */
    public Appliance getAppliance() {
        return null;
    }

    public MotionSensor getMotionSensor() {
        return null;
    }

    /**
     * Retrieves the UserInputSensor associated with this device system.
     * The sensor is used to detect and process user inputs for this system.
     *
     * @return An instance of UserInputSensor associated with this system.
     */
    public UserInputSensor getUserInputSensor() {
        return null;
    }

    public Report accept(ReportVisitor visitor) {
        return visitor.visitDeviceSystem(this);
    }

    @Override
    public void onTick() {}

    public double getTotalConsumption() {
        return 0;
    }

    public List<IDevice> getDevices() {
        return null;
    }

    public void repair() {
        for(IDevice device : getDevices()) {
            if(device.getState() == DeviceState.MALFUNCTION) {
                device.repair();
            }
        }
    }
}
