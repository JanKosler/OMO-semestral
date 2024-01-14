package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import lombok.Getter;

import java.util.ArrayList;
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
    private List<Floor> floors;

    private final Temperature internalTemperature;
    private final Temperature externalTemperature;

    public House(int houseID, int houseNumber, String address, Temperature internalTemperature, Temperature externalTemperature) {
        this.houseID = houseID;
        this.houseNumber = houseNumber;
        this.address = address;
        this.internalTemperature = internalTemperature;
        this.externalTemperature = externalTemperature;
        this.floors = new ArrayList<>();
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
        boolean exists = floors.stream().anyMatch(f -> f.getFloorLevel() == floor.getFloorLevel());
        if (exists) {
            throw new IllegalArgumentException("Floor with this number already exists.");
        }
        floors.add(floor);
    }

    /**
     * Gets a floor by its level.
     * @param level Level of the floor.
     * @return Floor with the given level.
     */
    public Floor getFLoor(int level) {
        return floors.stream()
                .filter(floor -> floor.getFloorLevel() == level)
                .findFirst()
                .orElse(null);
    }
    /**
     * Gets all devices in the house.
     * @return List of devices.
     */
    @Override
    public List<IDevice> getAllDevices() {
        return floors.stream()
                .flatMap(floor -> floor.getAllDevices().stream())
                .toList();
    }

    /**
     * Gets all device systems in the house.
     * @return List of device systems.
     */
    @Override
    public List<DeviceSystem> getAllDeviceSystems() {
        return floors.stream()
                .flatMap(floor -> floor.getAllDeviceSystems().stream())
                .toList();
    }

    /**
     * Gets all people in the house.
     * @return List of people.
     */
    @Override
    public List<Human> getAllPeople() {
        return floors.stream()
                .flatMap(floor -> floor.getAllPeople().stream())
                .toList();
    }

    /**
     * Gets all pets in the house.
     * @return List of pets.
     */
    @Override
    public List<Pet> getAllPets() {
        return floors.stream()
                .flatMap(floor -> floor.getAllPets().stream())
                .toList();
    }

    @Override
    public String toString() {
        return String.format("\"House\": { \"houseID\": %d,\n floorsCount: %d", houseID, floors.size());
    }

    public Report accept(ReportVisitor visitor) {
        return visitor.visitHouse(this);
    }
}
