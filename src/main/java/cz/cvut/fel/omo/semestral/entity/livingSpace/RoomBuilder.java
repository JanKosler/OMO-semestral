package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

import java.util.List;

public class RoomBuilder implements IRoomBuilder<Room> {
    List<IDevice> deviceList;
    List<DeviceSystem> deviceSystems;
    List<Being> inhabitants;

    public RoomBuilder addDevice(IDevice device) {
        this.deviceList.add(device);
        return this;
    }

    public RoomBuilder addDeviceSystem(DeviceSystem deviceSystem) {
        this.deviceSystems.add(deviceSystem);
        return this;
    }

    public RoomBuilder addPerson(Being person) {
        this.inhabitants.add(person);
        return this;
    }
    @Override
    public Room build() {
        return new Room(this);
    }
}
