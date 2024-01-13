package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.common.enums.Temperature;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Represents a house.
 */
@Getter
public class House implements ILivingSpace {
    /** Unique ID of the house. */
    private final int houseID;
    /** Number of the house. */
    private final int houseNumber;
    /** Address of the house. */
    private final String address;

    /** List of floors in the house. */
    private Map<Integer, Floor> floors;

    private final Temperature internalTemperature;
    private final Temperature externalTemperature;

    public House(int houseID, int houseNumber, String address, Temperature internalTemperature, Temperature externalTemperature) {
        this.houseID = houseID;
        this.houseNumber = houseNumber;
        this.address = address;
        this.internalTemperature = internalTemperature;
        this.externalTemperature = externalTemperature;
        this.floors = new java.util.HashMap<>();
    }
    public House(int houseID, int houseNumber, String address, Temperature internalTemperature, Temperature externalTemperature, List<Floor> floors) {
        this(houseID, houseNumber, address, internalTemperature, externalTemperature);
        for (Floor floor : floors) {
            this.addFloor(floor);
        }
    }
    /**
     * Adds a floor to the house.
     * @param floor Floor to add.
     * @throws IllegalArgumentException If a floor with the same number already exists.
     */
    public void addFloor(Floor floor) throws IllegalArgumentException {
        if (floors.containsKey(floor.getFloorLevel())) {
            throw new IllegalArgumentException("Floor with this number already exists.");
        }
        floors.put(floor.getFloorLevel(), floor);
    }

    /**
     * Gets a floor by its number.
     * @param floorNumber Number of the floor.
     * @return Floor with the given number.
     */
    public Floor getFLoor(int floorNumber) {
        return floors.get(floorNumber);
    }

    /**
     * Gets all devices in the house.
     * @return List of devices.
     */
    @Override
    public List<IDevice> getAllDevices() {
        return floors.entrySet().stream()
                .flatMap(entry -> entry.getValue().getAllDevices().stream())
                .toList();
    }

    /**
     * Gets all device systems in the house.
     * @return List of device systems.
     */
    @Override
    public List<DeviceSystem> getAllDeviceSystems() {
        return floors.entrySet().stream()
                .flatMap(entry -> entry.getValue().getAllDeviceSystems().stream())
                .toList();
    }

    /**
     * Gets all people in the house.
     * @return List of people.
     */
    @Override
    public List<Human> getAllPeople() {
        return floors.entrySet().stream()
                .flatMap(entry -> entry.getValue().getAllPeople().stream())
                .toList();
    }

    /**
     * Gets all pets in the house.
     * @return List of pets.
     */
    @Override
    public List<Pet> getAllPets() {
        return floors.entrySet().stream()
                .flatMap(entry -> entry.getValue().getAllPets().stream())
                .toList();
    }

    @Override
    public String toString() {
        return String.format("\"House\": { \"houseID\": %d,\n floorsCount: %d", houseID, floors.size());
    }
}
