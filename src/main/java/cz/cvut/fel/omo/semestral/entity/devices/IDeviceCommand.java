package cz.cvut.fel.omo.semestral.entity.devices;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;

/**
 * Interface for implementing command execution functionality in devices.
 * This interface allows devices to respond to various commands defined in {@link DeviceCommand}.
 */
public interface IDeviceCommand {

    /**
     * Executes a given command on the device.
     *
     * @param command The command to be executed, as defined in {@link DeviceCommand}.
     */
    void executeCommand(DeviceCommand command);
}
