package cz.cvut.fel.omo.semestral.entity.livingSpace;

import cz.cvut.fel.omo.semestral.entity.beings.Being;
import cz.cvut.fel.omo.semestral.entity.devices.IDevice;
import cz.cvut.fel.omo.semestral.entity.systems.DeviceSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for the {@link Garage} class. Extension of {@link RoomBuilder}.
 */
public class GarageBuilder implements IRoomBuilder<Garage> {
    /** List of sport equipment in the garage. */
    List<SportEquipment> sportEquipmentList;
    /** Room builder. */
    RoomBuilder roomBuilder;

    /**
     * Constructs a new {@code GarageBuilder} with default settings.
     */
    public GarageBuilder() {
        this.roomBuilder = new RoomBuilder();
        sportEquipmentList = new ArrayList<>();
    }

    /**
     * Sets the unique identifier for the garage room.
     *
     * @param roomID The unique identifier for the garage room.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder setRoomID(int roomID) {
        this.roomBuilder.setRoomID(roomID);
        return this;
    }

    /**
     * Sets the name of the garage room.
     *
     * @param roomName The name of the garage room.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder setRoomName(String roomName) {
        this.roomBuilder.setRoomName(roomName);
        return this;
    }

    /**
     * Adds a device to the garage.
     *
     * @param device The device to be added to the garage.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder addDevice(IDevice device) {
        this.roomBuilder.addDevice(device);
        return this;
    }

    /**
     * Adds a device system to the garage.
     *
     * @param deviceSystem The device system to be added to the garage.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder addDeviceSystem(DeviceSystem deviceSystem) {
        this.roomBuilder.addDeviceSystem(deviceSystem);
        return this;
    }

    /**
     * Adds a person to the garage.
     *
     * @param person The person to be added to the garage.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder addPerson(Being person) {
        this.roomBuilder.addPerson(person);
        return this;
    }

    /**
     * Adds a list of sport equipment to the garage.
     *
     * @param sportEquipment A list of sport equipment to be added to the garage.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder addSportEquipment(List<SportEquipment> sportEquipment) {
        for (SportEquipment equipment : sportEquipment)
            this.addSportEquipment(equipment);
        return this;
    }

    /**
     * Adds sport equipment to the garage.
     *
     * @param sportEquipment The sport equipment to be added to the garage.
     * @return This {@code GarageBuilder} instance for method chaining.
     */
    public GarageBuilder addSportEquipment(SportEquipment sportEquipment) {
        this.sportEquipmentList.add(sportEquipment);
        return this;
    }

    /**
     * Creates a new garage object.
     * @return Garage object.
     */
    @Override
    public Garage build() {
        return new Garage(this);
    }
}
