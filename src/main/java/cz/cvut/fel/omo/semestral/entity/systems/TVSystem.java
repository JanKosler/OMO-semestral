package cz.cvut.fel.omo.semestral.entity.systems;

import cz.cvut.fel.omo.semestral.common.enums.UserInputType;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.devices.appliances.TV;
import cz.cvut.fel.omo.semestral.entity.devices.controllers.TVController;
import cz.cvut.fel.omo.semestral.entity.devices.sensors.UserInputSensor;
import cz.cvut.fel.omo.semestral.entity.livingSpace.Room;
import lombok.Getter;

import java.util.List;

/**
 * TVSystem is a system that holds a TV, its controller and a user input sensor.
 * @see DeviceSystem base class
 */
@Getter
public class TVSystem extends DeviceSystem {
    /** The TV device */
    public final TV tv;
    /** The TV controller */
    public final TVController controller;
    /** The user input sensor */
    public final UserInputSensor userInputSensor;
    /** The list of UserInputTypes that this system can process */
    private final List<UserInputType> allowedUserInputTypes = List.of(UserInputType.TV_VOLUME, UserInputType.TV_CHANNEL);
    private final int deviceSystemID;

    public TVSystem(int deviceSystemID, TV tv, TVController controller, UserInputSensor userInputSensor) {
        this.deviceSystemID = deviceSystemID;
        this.tv = tv;
        this.controller = controller;
        this.userInputSensor = userInputSensor;
        turnOn();
    }

    /**
     * Retrieves the TV associated with this system.
     * @return An instance of TV associated with this system.
     */
    @Override
    public TV getAppliance() {
        return tv;
    }

    /**
     * Retrieves the UserInputSensor associated with this system.
     * @return An instance of UserInputSensor associated with this system.
     */
    @Override
    public UserInputSensor getUserInputSensor() {
        return userInputSensor;
    }

    /**
     * Turns on the whole TV system.
     */

    @Override
    public double getTotalConsumption() {
        return tv.getTotalPowerConsumption() + controller.getTotalPowerConsumption() + userInputSensor.getTotalPowerConsumption();
    }

    @Override
    public List<IDevice> getDevices() {
        return List.of(tv, controller, userInputSensor);
    }

    @Override
    public void onTick() {
        userInputSensor.onTick();
        controller.onTick();
        tv.onTick();
    }

    @Override
    public String toString() {
        return "TVSystem{" + "deviceSystemID=" + deviceSystemID + '}';
    }

    @Override
    public void setRoom(Room room) {
        return;
    }
}
