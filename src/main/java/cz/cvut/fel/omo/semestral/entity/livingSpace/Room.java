package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Getter;

import java.util.List;

/**
 * Represents a room in a house.
 */
@Getter
public class Room implements ILivingSpace {
    /** Unique ID of the room */
    private final int roomID;
    /** Name of the room */
    private final String roomName;
    /** List of all devices in the room */
    private List<IDevice> deviceList;
    /** List of device systems in the room */
    private List<DeviceSystem> deviceSystems;
    /** List of people in the room */
    private List<Being> inhabitants;

    /**
     * Creates a new room.
     * @param builder Room builder.
     */
    public Room(RoomBuilder builder) {
        this.roomID = builder.roomID;
        this.roomName = builder.roomName;
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
    public List<Human> getAllPeople() {
        return inhabitants.stream()
                .filter(Human.class::isInstance)
                .map(Human.class::cast)
                .toList();
    }

    /**
     * Gets all pets in the room implementation.
     *
     * @return List of pets.
     */
    public List<Pet> getAllPets() {
        return inhabitants.stream()
                .filter(Pet.class::isInstance)
                .map(Pet.class::cast)
                .toList();
    }

    public static RoomBuilder roomBuilder() {
        return new RoomBuilder();
    }
}
