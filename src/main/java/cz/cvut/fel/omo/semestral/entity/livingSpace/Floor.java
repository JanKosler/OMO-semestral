package cz.cvut.fel.omo.semestral.entity.livingSpace;


import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a floor in a house.
 */
@Builder
@Getter
public class Floor implements ILivingSpace {
    /** Unique ID of the floor */
    private final int floorID;
    /** Name of the floor */
    private final String floorName;
    /** Floor level number in the house */
    private final int floorLevel;
    /** List of rooms on the floor */
    private final List<Room> rooms;

    /**
     * Creates a new floor.
     * @param floorID Unique ID of the floor.
     * @param floorName Name of the floor.
     * @param floorLevel Floor level number in the house.
     * @param rooms List of rooms on the floor.
     */
    public Floor(int floorID, String floorName, int floorLevel, List<Room> rooms) {
        this.floorID = floorID;
        this.floorName = floorName;
        this.floorLevel = floorLevel;
        this.rooms = rooms;
    }

    /**
     * Returns all devices on the floor.
     * @return List of devices.
     */
    @Override
    public List<IDevice> getAllDevices() {
        return rooms.stream().flatMap(room -> room.getAllDevices().stream()).toList();
    }

    /**
     * Returns all device systems on the floor.
     * @return List of device systems.
     */
    @Override
    public List<DeviceSystem> getAllDeviceSystems() {
        return rooms.stream().flatMap(room -> room.getAllDeviceSystems().stream()).toList();
    }

    /**
     * Returns all people on the floor.
     * @return List of people.
     */
    @Override
    public List<Human> getAllPeople() {
        return rooms.stream().flatMap(room -> room.getAllPeople().stream()).toList();
    }

    /**
     * Returns all pets on the floor.
     * @return List of pets.
     */
    @Override
    public List<Pet> getAllPets() {
        return rooms.stream().flatMap(room -> room.getAllPets().stream()).toList();
    }

    /**
     * Returns all beings on the floor.
     * @return List of beings.
     */
    @Override
    public List<Being> getAllBeings() {
        return rooms.stream().flatMap(room -> room.getAllBeings().stream()).toList();
    }
}
