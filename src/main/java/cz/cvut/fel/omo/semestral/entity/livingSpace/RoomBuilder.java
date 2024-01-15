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

    /**
     * Constructs a new {@code RoomBuilder} instance with default settings.
     * Initializes lists for devices, device systems, and inhabitants.
     */
    public RoomBuilder() {
        this.deviceList = new java.util.LinkedList<>();
        this.deviceSystems = new java.util.LinkedList<>();
        this.inhabitants = new java.util.LinkedList<>();
    }

    /**
     * Sets the unique ID of the room being built.
     *
     * @param roomID The unique ID of the room.
     * @return This {@code RoomBuilder} instance for method chaining.
     */
    public RoomBuilder setRoomID(int roomID) {
        this.roomID = roomID;
        return this;
    }

    /**
     * Sets the name of the room being built.
     *
     * @param roomName The name of the room.
     * @return This {@code RoomBuilder} instance for method chaining.
     */
    public RoomBuilder setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Adds a device to the list of devices in the room being built.
     *
     * @param device The device to be added.
     * @return This {@code RoomBuilder} instance for method chaining.
     */
    public RoomBuilder addDevice(IDevice device) {
        this.deviceList.add(device);
        return this;
    }

    /**
     * Adds a device system to the list of device systems in the room being built.
     *
     * @param deviceSystem The device system to be added.
     * @return This {@code RoomBuilder} instance for method chaining.
     */
    public RoomBuilder addDeviceSystem(DeviceSystem deviceSystem) {
        this.deviceSystems.add(deviceSystem);
        return this;
    }

    /**
     * Adds a person to the list of inhabitants in the room being built.
     *
     * @param person The person to be added.
     * @return This {@code RoomBuilder} instance for method chaining.
     */
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
