package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.beings.Human;
import cz.cvut.fel.omo.semestral.entity.beings.Pet;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import cz.cvut.fel.omo.semestral.reporting.Report;
import cz.cvut.fel.omo.semestral.reporting.ReportVisitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Floor> floors;
    /** Internal temperature of the house. */
    private final Temperature internalTemperature;
    /** External temperature of the house. */
    private final Temperature externalTemperature;


    /**
     * Constructs a new {@code House} object with the specified parameters and a list of floors.
     *
     * @param houseID The unique ID of the house.
     * @param houseNumber The number of the house.
     * @param address The address of the house.
     * @param internalTemperature The internal temperature of the house.
     * @param externalTemperature The external temperature of the house.
     */
    public House(int houseID, int houseNumber, String address, Temperature internalTemperature, Temperature externalTemperature) {
        this.houseID = houseID;
        this.houseNumber = houseNumber;
        this.address = address;
        this.internalTemperature = internalTemperature;
        this.externalTemperature = externalTemperature;
        this.floors = new ArrayList<>();
    }

    /**
     * Constructs a new {@code House} object with the specified parameters and a list of floors.
     *
     * @param houseID The unique ID of the house.
     * @param houseNumber The number of the house.
     * @param address The address of the house.
     * @param internalTemperature The internal temperature of the house.
     * @param externalTemperature The external temperature of the house.
     * @param floors A list of floors within the house.
     */
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

    /**
     * Gets all beings in the house.
     * @return List of beings.
     */
    @Override
    public List<Being> getAllBeings() {
        return floors.stream()
                .flatMap(floor -> floor.getAllBeings().stream())
                .toList();
    }

    /**
     * Gets garage in the house.
     * @return Garage
     */
    public Garage getGarage() {
        return floors.stream().flatMap(floor -> floor.getRooms().stream())
                .filter(room -> room instanceof Garage)
                .map(room -> (Garage) room)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("\"House\": { \"houseID\": %d,\n floorsCount: %d", houseID, floors.size());
    }

    /**
     * Accepts a visitor and returns a report.
     * @param visitor Visitor to accept.
     * @return Report.
     */
    public Report accept(ReportVisitor visitor) {
        return visitor.visitHouse(this);
    }
}