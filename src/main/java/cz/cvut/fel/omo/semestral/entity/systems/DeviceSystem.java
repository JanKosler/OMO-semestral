package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.Appliance;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.MotionSensor;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import cz.cvut.fel.omo.semestral.tick.Tickable;
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
        return;
    }

    /**
     * Turns off the devices associated with this system.
     * This method should be overridden to provide specific turn-off behavior.
     */
    public void turnOff() {
        return;
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
    public void onTick() {
        return;
    }

    public double getTotalConsumption() {
        return 0;
    }

    public List<IDevice> getDevices() {
        return null;
    }
}
