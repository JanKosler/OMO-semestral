package cz.cvut.fel.omo.semestral.entity.devices.livingSpace;

import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Represents a house.
 */
@Getter
@AllArgsConstructor
public class House implements ILivingSpace {
    /** Unique ID of the house. */
    @Setter
    private int houseID;

    /** List of floors in the house. */
    private Map<Integer, Floor> floors;

    /**
     * Adds a floor to the house.
     * @param floor Floor to add.
     * @throws IllegalArgumentException If a floor with the same number already exists.
     */
    public void addFloor(Floor floor) throws IllegalArgumentException {
        if (floors.containsKey(floor.getFloorNumber())) {
            throw new IllegalArgumentException("Floor with this number already exists.");
        }
        floors.put(floor.getFloorNumber(), floor);
    }

    /**
     * Gets a floor by its number.
     * @param floorNumber Number of the floor.
     * @return Floor with the given number.
     */
    public Floor getFLoor(int floorNumber) {
        return floors.get(floorNumber);
    }

    @Override
    public List<IDevice> getAllDevices() {
        return floors.entrySet().stream()
                .flatMap(entry -> entry.getValue().getAllDevices().stream())
                .toList();
    }

    @Override
    public String toString() {
        return String.format("\"House\": { \"houseID\": %d,\n floorsCount: %d", houseID, floors.size());
    }
}
