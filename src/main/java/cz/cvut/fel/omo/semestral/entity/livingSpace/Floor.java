package cz.cvut.fel.omo.semestral.entity.livingSpace;


import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;
import lombok.Getter;

import java.util.List;

/**
 * Represents a floor in a house.
 */
@Getter
public class Floor implements ILivingSpace {
    /** Floor level number in the house */
    private int floorNumber;

    /** List of rooms on the floor */
    private List<Room> rooms;

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
}
