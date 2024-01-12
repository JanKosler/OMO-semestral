package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Getter;

import java.util.List;

@Getter
public class Room implements ILivingSpace {
    /** Unique ID of the room */
    private int roomID;
    /** Name of the room */
    private String roomName;

    /** List of all devices in the room */
    private final List<IDevice> deviceList;

    /** List of device systems in the room */
    private final List<DeviceSystem> deviceSystems;

    /** List of people in the room */
    private final List<Being> inhabitants;

    public Room(RoomBuilder builder) {
        this.deviceList = builder.deviceList;
        this.deviceSystems = builder.deviceSystems;
        this.inhabitants = builder.inhabitants;
    }

    /**
     * Gets all devices in the room implementation.
     *
     * @return List of devices.
     */
    @Override
    public List<IDevice> getAllDevices() {
        return deviceList;
    }

    /**
     * Gets all device systems in the room implementation.
     *
     * @return List of device systems.
     */
    @Override
    public List<DeviceSystem> getAllDeviceSystems() {
        return deviceSystems;
    }

    /**
     * Gets all people in the room implementation.
     *
     * @return List of people.
     */
    public List<Being> getAllPeople() {
        return inhabitants;
    }

}
