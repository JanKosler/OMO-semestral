package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

import java.util.List;

public class RoomBuilder implements IRoomBuilder<Room> {
    /** Unique ID of the room */
    int roomID;
    /** Name of the room */
    String roomName;
    /** List of all devices in the room */
    List<IDevice> deviceList;
    /** List of device systems in the room */
    List<DeviceSystem> deviceSystems;
    /** List of people in the room */
    List<Being> inhabitants;

    public RoomBuilder() {
        this.deviceList = new java.util.LinkedList<>();
        this.deviceSystems = new java.util.LinkedList<>();
        this.inhabitants = new java.util.LinkedList<>();
    }
    public RoomBuilder setRoomID(int roomID) {
        this.roomID = roomID;
        return this;
    }
    public RoomBuilder setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }
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

    /**
     * Creates a new room object.
     * @return Room object.
     */
    @Override
    public Room build() {
        return new Room(this);
    }
}
