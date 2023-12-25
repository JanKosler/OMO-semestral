package cz.cvut.fel.omo.semestral.entity.devices;

import cz.cvut.fel.omo.semestral.common.enums.DeviceCommand;

public interface IDeviceCommand {

    public void executeCommand(DeviceCommand command);

}
